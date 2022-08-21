#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
@Author         : thanatos518
@Email          : Gilgamish_Enkidu@163.com
@PROJECT_NAME   : pythonProject
@File           : xgappV1.py
@Software       : PyCharm
@Time           : 2022-04-12 21:50
"""

import base64
import json

import requests
from fastapi import FastAPI

app = FastAPI()
apiUrl = "http://qqtvapp.com/xgapp.php/v1"
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36'}
parseUrlMap = {}


@app.get("/")
async def read_parameters(filter: bool = None,
                          t: str = None, pg: str = None, ext: str = None,
                          ids: str = None,
                          flag: str = None, play: str = None,
                          wd: str = None, quick: bool = None):
    if ids:
        return detailContent(ids)
    elif t and pg:
        return categoryContent(t, pg, filter, ext)
    elif flag and play:
        return playerContent(flag, play)
    elif wd:
        return searchContent(wd, quick)
    else:
        return homeContent(filter)


def homeContent(filter=True):
    result = {}
    if apiUrl.find("api.php/app") != -1 or apiUrl.find("xgapp.php/") != -1:
        # 以下为分类和筛选项处理代码
        filter_url = apiUrl + "/nav?token="
        filter_url_response = requests.get(filter_url, headers=headers, verify=False, timeout=20)
        res_json = json.loads(filter_url_response.text.strip())
        filter_url_data = res_json["data"]
        type_class = []
        filterConfig = {}
        for jObj in filter_url_data:
            newCls = {}
            type_name = jObj["type_name"]
            type_id = jObj["type_id"]
            newCls["type_name"] = type_name
            newCls["type_id"] = type_id
            type_class.append(newCls)
            extendsAll = []
            if filter or filter is None:
                type_extend = jObj["type_extend"]
                for key in type_extend.keys():
                    if key == "class":
                        name = "类型"
                    elif key == "area":
                        name = "地区"
                    elif key == "lang":
                        name = "语言"
                    elif key == "year":
                        name = "年份"
                    else:
                        break
                    # 单条筛选类型
                    newTypeExtend = {"key": key, "name": name}
                    # 筛选项nv对数组
                    newTypeExtendKV = []
                    kvall = {"n": "全部", "v": ""}
                    newTypeExtendKV.append(kvall)
                    for f in type_extend[key].strip().split(","):
                        if len(f) != 0:
                            kv = {"n": f, "v": f}
                            newTypeExtendKV.append(kv)
                    newTypeExtend["value"] = newTypeExtendKV
                    extendsAll.append(newTypeExtend)
                filterConfig[type_id] = extendsAll
        result["class"] = type_class
        if len(filterConfig) != 0:
            result["filters"] = filterConfig
        # 以下推荐页处理代码
        url = apiUrl + "/index_video?token="
        url_response = requests.get(url, headers=headers, verify=False, timeout=20)
        res_json = json.loads(url_response.text.strip())
        url_data = res_json["data"]
        videos_lsit = []
        for d in url_data:
            vlist = d["vlist"]
            for l in vlist:
                videos = {}
                videos["vod_id"] = l["vod_id"]
                videos["vod_name"] = l["vod_name"]
                videos["vod_pic"] = l["vod_pic"]
                videos["vod_remarks"] = l["vod_remarks"]
                videos_lsit.append(videos)
        result["list"] = videos_lsit
        return result


def categoryContent(tid, pg, filter=True, extend=None):
    result = {}
    if apiUrl.find("api.php/app") != -1 or apiUrl.find("xgapp.php/") != -1:
        # 以下分类页处理代码
        url = apiUrl + "/video?tid=" + tid + "&pg=" + pg
        if len(extend) > 0:
            dic = base64.b64decode(extend)
            extDic = json.loads(dic)
            extStr = ""
            for k in extDic.keys():
                extStr = extStr + "&" + k + "=" + extDic[k]
            url = apiUrl + "/video?tid=" + tid + "&pg=" + pg + extStr
        url_response = requests.get(url, headers=headers, verify=False, timeout=20)
        res_json = json.loads(url_response.text.strip())
        url_data = res_json["data"]
        videos_lsit = []
        for l in url_data:
            videos = {}
            videos["vod_id"] = l["vod_id"]
            videos["vod_name"] = l["vod_name"]
            videos["vod_pic"] = l["vod_pic"]
            videos["vod_remarks"] = l["vod_remarks"]
            videos_lsit.append(videos)
        result["list"] = videos_lsit
        return result


def detailContent(ids):
    global parseUrlMap
    result = {}
    if apiUrl.find("api.php/app") != -1 or apiUrl.find("xgapp.php/") != -1:
        # 以下详情页处理代码
        url = apiUrl + "/video_detail?id=" + ids
        url_response = requests.get(url, headers=headers, verify=False, timeout=20)
        res_json = json.loads(url_response.text.strip())
        url_data = res_json["data"]["vod_info"]
        videos = {}
        videos["vod_id"] = url_data["vod_id"]
        videos["vod_name"] = url_data["vod_name"]
        videos["vod_pic"] = url_data["vod_pic"]
        videos["type_name"] = url_data["vod_class"]
        videos["vod_year"] = url_data["vod_year"]
        videos["vod_area"] = url_data["vod_area"]
        videos["vod_remarks"] = url_data["vod_remarks"]
        videos["vod_actor"] = url_data["vod_actor"]
        videos["vod_director"] = url_data["vod_director"]
        videos["vod_content"] = url_data["vod_content"]
        vod_url_with_player = url_data["vod_url_with_player"]
        flag_list = []
        playUrls_list = []
        for vod in vod_url_with_player:
            vod_flag = vod["code"]
            flag_list.append(vod_flag)
            playUrls = vod["url"]
            playUrls_list.append(playUrls)
            purl = vod["parse_api"]
            parseUrlMap[vod_flag] = purl
        print(parseUrlMap)
        videos["vod_play_from"] = "$$$".join(flag_list)
        videos["vod_play_url"] = "$$$".join(playUrls_list)
        videos_list = []
        videos_list.append(videos)
        result["list"] = videos_list
        return result


def playerContent(flag, id):
    global parseUrlMap
    # parseUrlMap = {'rx': 'https://svip.rongxingvr.top/api/?key=pJZH2q4XbLM2820tS8&url=',
    #                'ltnb': 'https://ltnb.jeeves.vip/home/api?type=ys&uid=3316654&key=acdhlqrtADFHJLR139&url=',
    #                'mgtv': 'https://svip.renrenmi.cc:2222/api/?key=SZqn2js1LmwKPO39ww&url=',
    #                'youku': 'https://svip.renrenmi.cc:2222/api/?key=SZqn2js1LmwKPO39ww&url=',
    #                'qiyi': 'https://svip.renrenmi.cc:2222/api/?key=SZqn2js1LmwKPO39ww&url=',
    #                'qq': 'https://svip.renrenmi.cc:2222/api/?key=SZqn2js1LmwKPO39ww&url='}
    result = {}
    if apiUrl.find("api.php/app") != -1 or apiUrl.find("xgapp.php/") != -1:
        # 以下播放页处理代码
        try:
            if flag in parseUrlMap.keys():
                parseUrl = parseUrlMap.get(flag)
                result = {}
                if len(parseUrl) > 0:
                    url = parseUrlMap.get(flag) + id
                    url_response = requests.get(url, headers=headers, verify=False, timeout=20)
                    res_json = json.loads(url_response.text.strip())
                    result = jsonParse(id, res_json)
                    result["parse"] = 0
                    result["playUrl"] = ""
                    return result
                else:
                    result["parse"] = 0
                    result["playUrl"] = ""
                    result["url"] = id
                    return result
            else:
                result["parse"] = 1
                result["playUrl"] = ""
                result["url"] = id
                return result
        except:
            result["parse"] = 1
            result["playUrl"] = ""
            result["url"] = id
            return result


def searchContent(key, quick=True):
    result = {}
    if apiUrl.find("api.php/app") != -1 or apiUrl.find("xgapp.php/") != -1:
        # 以下搜索页处理代码
        url = apiUrl + "/search?text=" + key + "&pg=1"
        url_response = requests.get(url, headers=headers, verify=False, timeout=20)
        res_json = json.loads(url_response.text.strip())
        url_data = res_json["data"]
        videos_lsit = []
        for l in url_data:
            videos = {}
            videos["vod_id"] = l["vod_id"]
            videos["vod_name"] = l["vod_name"]
            videos["vod_pic"] = l["vod_pic"]
            videos["vod_remarks"] = l["vod_remarks"]
            videos_lsit.append(videos)
        result["list"] = videos_lsit
        return result


def jsonParse(input, json):
    jsonPlayData = json
    if "data" in json.keys():
        jsonPlayData = jsonPlayData["data"]
    url = jsonPlayData["url"].strip()
    if url.startswith("//"):
        url = "https:" + url
    if not url.startswith("http"):
        return None
    if url == input:
        return None
    headers = {}
    if "header" in jsonPlayData.keys():
        headers = jsonPlayData["header"]
    if "headers" in jsonPlayData.keys():
        headers = jsonPlayData["headers"]
    ua = ""
    if "user-agent" in jsonPlayData.keys():
        ua = jsonPlayData["user-agent"]
    if "User-Agent" in jsonPlayData.keys():
        ua = jsonPlayData["User-Agent"]
    if len(ua) > 0:
        headers["User-Agent"] = ua
    referer = ""
    if "referer" in jsonPlayData.keys():
        referer = jsonPlayData["referer"]
    if "Referer" in jsonPlayData.keys():
        referer = jsonPlayData["Referer"]
    if len(referer) > 0:
        headers["referer"] = referer
    result = {}
    result["url"] = url
    if len(headers) > 0:
        result["header"] = headers
    return result


if __name__ == '__main__':
    import uvicorn

    uvicorn.run(app=app, host="0.0.0.0", port=8080, workers=1)
