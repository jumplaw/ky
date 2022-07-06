//
// Decompiled by Jadx (Simple) - 970ms
//
package com.github.catvod.spider;

import android.content.Context;
import android.text.TextUtils;
import com.github.catvod.crawler.Spider;
import com.github.catvod.crawler.SpiderDebug;
import com.github.catvod.spider.merge.Z;
import com.github.catvod.spider.merge.w1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class XBiubiu extends Spider {
    private JSONObject B5;
    private int[] FR;
    protected JSONObject HC;
    protected String Sm;

    public XBiubiu() {
        this.Sm = null;
        this.HC = null;
        this.FR = new int[2];
    }

    private JSONObject B5(String str, String str2, boolean z, HashMap<String, String> hashMap) {
        HC();     // Catch: Exception -> L13
        String str3 = w("url") + str + str2 + w("houzhui");     // Catch: Exception -> L13
        String Sm = Sm(str3);     // Catch: Exception -> L13
        if (w("shifouercijiequ").equals("1") == false) goto L6;
        Sm = uc(Sm, w("jiequqian"), w("jiequhou"), 0);     // Catch: Exception -> L13
    L6:
        String w = w("jiequshuzuqian");     // Catch: Exception -> L13
        String w2 = w("jiequshuzuhou");     // Catch: Exception -> L13
        b();     // Catch: Exception -> L13
        JSONArray jSONArray = new JSONArray();     // Catch: Exception -> L13
        String uc = uc(Sm, w, w2, 0);     // Catch: Exception -> L13
    L7:
        int[] iArr = this.FR;     // Catch: Exception -> L13
        if (iArr[1] == (-1)) goto L11;
        int i = iArr[1];     // Catch: Throwable -> L16
        String uc2 = uc(uc, w("biaotiqian"), w("biaotihou"), 0);     // Catch: Throwable -> L16
        String Sm2 = w1.Sm(str3, uc(uc, w("tupianqian"), w("tupianhou"), 0));     // Catch: Throwable -> L16
        String uc3 = uc(uc, w("lianjieqian"), w("lianjiehou"), 0);     // Catch: Throwable -> L16
        JSONObject jSONObject = new JSONObject();     // Catch: Throwable -> L16
        jSONObject.put("vod_id", uc2 + "$$$" + Sm2 + "$$$" + uc3);     // Catch: Throwable -> L16
        jSONObject.put("vod_name", uc2);     // Catch: Throwable -> L16
        jSONObject.put("vod_pic", Sm2);     // Catch: Throwable -> L16
        jSONObject.put("vod_remarks", "");     // Catch: Throwable -> L16
        jSONArray.put(jSONObject);     // Catch: Throwable -> L16
        uc = uc(Sm, w, w2, i);     // Catch: Throwable -> L16
    L11:
        JSONObject jSONObject2 = new JSONObject();     // Catch: Exception -> L13
        jSONObject2.put("page", str2);     // Catch: Exception -> L13
        jSONObject2.put("pagecount", Integer.MAX_VALUE);     // Catch: Exception -> L13
        jSONObject2.put("limit", 90);     // Catch: Exception -> L13
        jSONObject2.put("total", Integer.MAX_VALUE);     // Catch: Exception -> L13
        jSONObject2.put("list", jSONArray);     // Catch: Exception -> L13
        return jSONObject2;
    L13:
        e = move-exception;
        SpiderDebug.log(e);
        return null;
    }

    private String ZA(String str, String str2) {
        String optString = this.B5.optString(str);
        if (optString.isEmpty() == false) goto L5;
    L7:
        return str2;
    L5:
        if (optString.equals("空") == true) goto L7;
        return optString;
    }

    private void b() {
        int[] iArr = this.FR;
        iArr[0] = -1;
        iArr[1] = -1;
    }

    private String uc(String str, String str2, String str3, int i) {
        int indexOf = str.indexOf(str2, i);     // Catch: Throwable -> L9
        if (indexOf != (-1)) goto L7;
        b();     // Catch: Throwable -> L9
        return "";
    L7:
        int length = indexOf + str2.length();     // Catch: Throwable -> L9
        int indexOf2 = str.indexOf(str3, length);     // Catch: Throwable -> L9
        int[] iArr = this.FR;     // Catch: Throwable -> L9
        iArr[0] = length;     // Catch: Throwable -> L9
        iArr[1] = indexOf2;     // Catch: Throwable -> L9
        return str.substring(length, indexOf2);
    L9:
        b();
        return "";
    }

    private String w(String str) {
        return ZA(str, "");
    }

    protected HashMap<String, String> FR(String str) {
        HashMap<String, String> hashMap = new HashMap();
        String str2 = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.54 Safari/537.36";
        String trim = ZA("User", str2).trim();
        if (trim.isEmpty() == true) goto L5;
        str2 = trim;
    L5:
        hashMap.put("User-Agent", str2);
        return hashMap;
    }

    protected void HC() {
        if (this.HC != null) goto L15;
        String str = this.Sm;
        if (str == null) goto L16;
        if (str.startsWith("http") == false) goto L9;
        this.HC = new JSONObject(Z.w(this.Sm, null));     // Catch: JSONException -> L11
    L12:
        return;
    L9:
        this.HC = new JSONObject(this.Sm);     // Catch: JSONException -> L11
    L17:
        return;
    L16:
        return;
    }

    protected String Sm(String str) {
        SpiderDebug.log(str);
        return Z.w(str, FR(str));
    }

    public String categoryContent(String str, String str2, boolean z, HashMap<String, String> hashMap) {
        JSONObject B5 = B5(str, str2, z, hashMap);
        if (B5 != null) goto L6;
        return "";
    L6:
        return B5.toString();
    }

    public String detailContent(List<String> list) {
        String str = "$$$";
        String str2 = "";
        HC();     // Catch: Exception -> L42
        String[] split = list.get(0).split("\\$\\$\\$");     // Catch: Exception -> L42
        String Sm = Sm(w("url") + split[2]);     // Catch: Exception -> L42
        if (w("bfshifouercijiequ").equals("1") == false) goto L6;
        Sm = uc(Sm, w("bfjiequqian"), w("bfjiequhou"), 0);     // Catch: Exception -> L42
    L6:
        ArrayList arrayList = new ArrayList();     // Catch: Exception -> L42
        String w = w("bfjiequshuzuqian");     // Catch: Exception -> L42
        String w2 = w("bfjiequshuzuhou");     // Catch: Exception -> L42
        b();     // Catch: Exception -> L42
        boolean equals = w("bfyshifouercijiequ").equals("1");     // Catch: Exception -> L42
        String uc = uc(Sm, w, w2, 0);     // Catch: Exception -> L42
    L7:
        int[] iArr = this.FR;     // Catch: Exception -> L42
        if (iArr[1] == (-1)) goto L28;
        int i = iArr[1];     // Catch: Throwable -> L26
        if (equals == false) goto L14;
        equals = equals;
        uc = uc(uc, w("bfyjiequqian"), w("bfyjiequhou"), 0);     // Catch: Throwable -> L26
    L15:
        b();     // Catch: Throwable -> L26
        String uc2 = uc(uc, w("bfyjiequshuzuqian"), w("bfyjiequshuzuhou"), 0);     // Catch: Throwable -> L26
        ArrayList arrayList2 = new ArrayList();     // Catch: Throwable -> L26
    L16:
        int[] iArr2 = this.FR;     // Catch: Throwable -> L26
        str2 = str2;
        if (iArr2[1] == (-1)) goto L24;
        int i2 = iArr2[1];     // Catch: Throwable -> L47
        String uc3 = uc(uc2, w("bfbiaotiqian"), w("bfbiaotihou"), 0);     // Catch: Throwable -> L47
        split = split;
        String uc4 = uc(uc2, w("bflianjieqian"), w("bflianjiehou"), 0);     // Catch: Throwable -> L48
        arrayList2.add(uc3 + "$" + uc4);     // Catch: Throwable -> L48
        uc2 = uc(uc, w("bfyjiequshuzuqian"), w("bfyjiequshuzuhou"), i2);     // Catch: Throwable -> L48
        str = str;
        str2 = str2;
        split = split;
    L24:
        arrayList.add(TextUtils.join("#", arrayList2));     // Catch: Throwable -> L48
        uc = uc(Sm, w, w2, i);     // Catch: Throwable -> L48
        str = str;
        str2 = str2;
        split = split;
    L51:
        String str3 = split[1];     // Catch: Exception -> L40
        String str4 = split[0];     // Catch: Exception -> L40
        JSONObject jSONObject = new JSONObject();     // Catch: Exception -> L40
        jSONObject.put("vod_id", list.get(0));     // Catch: Exception -> L40
        jSONObject.put("vod_name", str4);     // Catch: Exception -> L40
        jSONObject.put("vod_pic", str3);     // Catch: Exception -> L40
        str2 = str2;
        jSONObject.put("type_name", str2);     // Catch: Exception -> L39
        jSONObject.put("vod_year", str2);     // Catch: Exception -> L39
        jSONObject.put("vod_area", str2);     // Catch: Exception -> L39
        jSONObject.put("vod_remarks", str2);     // Catch: Exception -> L39
        jSONObject.put("vod_actor", str2);     // Catch: Exception -> L39
        jSONObject.put("vod_director", str2);     // Catch: Exception -> L39
        jSONObject.put("vod_content", str2);     // Catch: Exception -> L39
        ArrayList arrayList3 = new ArrayList();     // Catch: Exception -> L39
        int i3 = 0;
    L35:
        if (i3 >= arrayList.size()) goto L37;
        StringBuilder sb = new StringBuilder();     // Catch: Exception -> L39
        sb.append("播放列表");     // Catch: Exception -> L39
        i3 = i3 + 1;     // Catch: Exception -> L39
        sb.append(i3);     // Catch: Exception -> L39
        arrayList3.add(sb.toString());     // Catch: Exception -> L39
        goto L35
    L37:
        String join = TextUtils.join(str, arrayList3);     // Catch: Exception -> L39
        String join2 = TextUtils.join(str, arrayList);     // Catch: Exception -> L39
        jSONObject.put("vod_play_from", join);     // Catch: Exception -> L39
        jSONObject.put("vod_play_url", join2);     // Catch: Exception -> L39
        JSONObject jSONObject2 = new JSONObject();     // Catch: Exception -> L39
        JSONArray jSONArray = new JSONArray();     // Catch: Exception -> L39
        jSONArray.put(jSONObject);     // Catch: Exception -> L39
        jSONObject2.put("list", jSONArray);     // Catch: Exception -> L39
        return jSONObject2.toString();
    L39:
        Exception e = e;
    L44:
        SpiderDebug.log(e);
        return str2;
    L40:
        e = e;
        str2 = str2;
        goto L44
    L14:
        equals = equals;
    L26:
        str = str;
        goto L51
    L28:
        str = str;
        str2 = str2;
    L42:
        e = e;
        goto L44
    }

    public String homeContent(boolean z) {
        HC();     // Catch: Exception -> L8
        JSONObject jSONObject = new JSONObject();     // Catch: Exception -> L8
        JSONArray jSONArray = new JSONArray();     // Catch: Exception -> L8
        String[] split = ZA("fenlei", "").split("#");     // Catch: Exception -> L8
        int length = split.length;     // Catch: Exception -> L8
        int i = 0;
    L4:
        if (i >= length) goto L6;
        String[] split2 = split[i].split("\\$");     // Catch: Exception -> L8
        JSONObject jSONObject2 = new JSONObject();     // Catch: Exception -> L8
        jSONObject2.put("type_name", split2[0]);     // Catch: Exception -> L8
        jSONObject2.put("type_id", split2[1]);     // Catch: Exception -> L8
        jSONArray.put(jSONObject2);     // Catch: Exception -> L8
        i = i + 1;     // Catch: Exception -> L8
        goto L4
    L6:
        jSONObject.put("class", jSONArray);     // Catch: Exception -> L8
        return jSONObject.toString();
    L8:
        e = move-exception;
        SpiderDebug.log(e);
        return "";
    }

    public String homeVideoContent() {
        HC();     // Catch: Exception -> L25
        if (w("shouye").equals("1") == false) goto L27;
        JSONArray jSONArray = new JSONArray();     // Catch: Exception -> L25
        String[] split = ZA("fenlei", "").split("#");     // Catch: Exception -> L25
        int length = split.length;     // Catch: Exception -> L25
        int i = 0;
    L8:
        if (i >= length) goto L23;
        JSONObject B5 = B5(split[i].split("\\$")[1], "1", false, new HashMap());     // Catch: Exception -> L25
        if (B5 == null) goto L20;
        JSONArray optJSONArray = B5.optJSONArray("list");     // Catch: Exception -> L25
        if (optJSONArray == null) goto L20;
        int i2 = 0;
    L15:
        if (i2 >= optJSONArray.length()) goto L20;
        if (i2 >= 5) goto L20;
        jSONArray.put(optJSONArray.getJSONObject(i2));     // Catch: Exception -> L25
        i2 = i2 + 1;     // Catch: Exception -> L25
    L20:
        if (jSONArray.length() >= 30) goto L23;
        i = i + 1;     // Catch: Exception -> L25
    L23:
        JSONObject jSONObject = new JSONObject();     // Catch: Exception -> L25
        jSONObject.put("list", jSONArray);     // Catch: Exception -> L25
        return jSONObject.toString();
    L27:
        return "";
    L25:
        e = move-exception;
        SpiderDebug.log(e);
        goto L27
    }

    public void init(Context context) {
        XBiubiu.super.init(context);
    }

    public String playerContent(String str, String str2, List<String> list) {
        HC();     // Catch: Exception -> L5
        JSONObject jSONObject = new JSONObject();     // Catch: Exception -> L5
        jSONObject.put("parse", 1);     // Catch: Exception -> L5
        jSONObject.put("playUrl", "");     // Catch: Exception -> L5
        jSONObject.put("url", w("url") + str2);     // Catch: Exception -> L5
        return jSONObject.toString();
    L5:
        e = move-exception;
        SpiderDebug.log(e);
        return "";
    }

    public String searchContent(String str, boolean z) {
        HC();     // Catch: Exception -> L24
        boolean equals = w("ssmoshi").equals("0");     // Catch: Exception -> L24
        String str2 = w("url") + w("sousuoqian") + str + w("sousuohou");     // Catch: Exception -> L24
        String Sm = Sm(str2);     // Catch: Exception -> L24
        JSONObject jSONObject = new JSONObject();     // Catch: Exception -> L24
        JSONArray jSONArray = new JSONArray();     // Catch: Exception -> L24
        String str3 = "list";
        int i = 0;
        if (equals == false) goto L11;
        JSONArray jSONArray2 = new JSONObject(Sm).getJSONArray(str3);     // Catch: Exception -> L24
    L8:
        if (i >= jSONArray2.length()) goto L10;
        JSONObject jSONObject2 = jSONArray2.getJSONObject(i);     // Catch: Exception -> L24
        String trim = jSONObject2.optString(w("jsname")).trim();     // Catch: Exception -> L24
        String trim2 = jSONObject2.optString(w("jsid")).trim();     // Catch: Exception -> L24
        String Sm2 = w1.Sm(str2, jSONObject2.optString(w("jspic")).trim());     // Catch: Exception -> L24
        JSONObject jSONObject3 = new JSONObject();     // Catch: Exception -> L24
        jSONObject3.put("vod_id", trim + "$$$" + Sm2 + "$$$" + w("sousuohouzhui") + trim2);     // Catch: Exception -> L24
        jSONObject3.put("vod_name", trim);     // Catch: Exception -> L24
        jSONObject3.put("vod_pic", Sm2);     // Catch: Exception -> L24
        jSONObject3.put("vod_remarks", "");     // Catch: Exception -> L24
        jSONArray.put(jSONObject3);     // Catch: Exception -> L24
        i = i + 1;     // Catch: Exception -> L24
        jSONArray2 = jSONArray2;
        jSONObject = jSONObject;
        str3 = str3;
        goto L8
    L10:
        JSONObject jSONObject4 = jSONObject;
        String str4 = str3;
    L22:
        jSONObject4.put(str4, jSONArray);     // Catch: Exception -> L24
        return jSONObject4.toString();
    L11:
        jSONObject4 = jSONObject;
        str4 = str3;
        if (w("sousuoshifouercijiequ").equals("1") == false) goto L14;
        Sm = uc(Sm, w("ssjiequqian"), w("ssjiequhou"), 0);     // Catch: Exception -> L24
    L14:
        String w = w("ssjiequshuzuqian");     // Catch: Exception -> L24
        String w2 = w("ssjiequshuzuhou");     // Catch: Exception -> L24
        b();     // Catch: Exception -> L24
        String uc = uc(Sm, w, w2, 0);     // Catch: Exception -> L24
    L15:
        int[] iArr = this.FR;     // Catch: Exception -> L24
        if (iArr[1] == (-1)) goto L22;
        int i2 = iArr[1];     // Catch: Throwable -> L27
        String uc2 = uc(uc, w("ssbiaotiqian"), w("ssbiaotihou"), 0);     // Catch: Throwable -> L27
        String Sm3 = w1.Sm(str2, uc(uc, w("sstupianqian"), w("sstupianhou"), 0));     // Catch: Throwable -> L27
        String uc3 = uc(uc, w("sslianjieqian"), w("sslianjiehou"), 0);     // Catch: Throwable -> L27
        JSONObject jSONObject5 = new JSONObject();     // Catch: Throwable -> L27
        jSONObject5.put("vod_id", uc2 + "$$$" + Sm3 + "$$$" + uc3);     // Catch: Throwable -> L27
        jSONObject5.put("vod_name", uc2);     // Catch: Throwable -> L27
        jSONObject5.put("vod_pic", Sm3);     // Catch: Throwable -> L27
        jSONObject5.put("vod_remarks", "");     // Catch: Throwable -> L27
        jSONArray.put(jSONObject5);     // Catch: Throwable -> L27
        uc = uc(Sm, w, w2, i2);     // Catch: Throwable -> L27
        str2 = str2;
    L24:
        e = move-exception;
        SpiderDebug.log(e);
        return "";
    }

    public void init(Context context, String str) {
        XBiubiu.super.init(context, str);
        this.Sm = str;
        if (str != null) goto L5;
        return;
    L5:
        if (str.contains("http") == true) goto L11;
        return;
    L11:
        this.B5 = new JSONObject(Z.w(this.Sm, null));     // Catch: Exception -> L8
        return;
    L8:
        e = move-exception;
        SpiderDebug.log(e);
    }
}
