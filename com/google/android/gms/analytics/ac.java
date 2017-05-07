// 
// Decompiled by Procyon v0.5.30
// 

package com.google.android.gms.analytics;

import android.os.Build$VERSION;
import java.util.HashSet;
import android.database.sqlite.SQLiteDatabase$CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.Collections;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import com.google.android.gms.internal.ef;
import java.util.Collection;
import android.content.ContentValues;
import java.util.Map;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Context;

class ac implements d
{
    private static final String vx;
    private final Context mContext;
    private final e sO;
    private i tg;
    private final String vA;
    private ab vB;
    private long vC;
    private final int vD;
    private final a vy;
    private volatile n vz;
    
    static {
        vx = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", "hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id");
    }
    
    ac(final e e, final Context context) {
        this(e, context, "google_analytics_v4.db", 2000);
    }
    
    ac(final e so, final Context context, final String va, final int vd) {
        this.mContext = context.getApplicationContext();
        this.vA = va;
        this.sO = so;
        this.tg = new i() {
            @Override
            public long currentTimeMillis() {
                return System.currentTimeMillis();
            }
        };
        this.vy = new a(this.mContext, this.vA);
        this.vz = new ah((HttpClient)new DefaultHttpClient(), this.mContext);
        this.vC = 0L;
        this.vD = vd;
    }
    
    private SQLiteDatabase L(final String s) {
        try {
            return this.vy.getWritableDatabase();
        }
        catch (SQLiteException ex) {
            aa.z(s);
            return null;
        }
    }
    
    private void a(final Map<String, String> map, long long1, final String s) {
        final SQLiteDatabase l = this.L("Error opening database for putHit");
        if (l == null) {
            return;
        }
        final ContentValues contentValues = new ContentValues();
        contentValues.put("hit_string", w(map));
        contentValues.put("hit_time", long1);
        while (true) {
            Label_0149: {
                if (!map.containsKey("AppUID")) {
                    break Label_0149;
                }
                String s2;
                try {
                    long1 = Long.parseLong(map.get("AppUID"));
                    contentValues.put("hit_app_id", long1);
                    s2 = s;
                    if (s == null) {
                        s2 = "http://www.google-analytics.com/collect";
                    }
                    if (s2.length() == 0) {
                        aa.z("Empty path: not sending hit");
                        return;
                    }
                }
                catch (NumberFormatException ex) {
                    long1 = 0L;
                    continue;
                }
                contentValues.put("hit_url", s2);
                try {
                    l.insert("hits2", (String)null, contentValues);
                    this.sO.r(false);
                    return;
                }
                catch (SQLiteException ex2) {
                    aa.z("Error storing hit");
                    return;
                }
            }
            long1 = 0L;
            continue;
        }
    }
    
    private void a(final Map<String, String> map, final Collection<ef> collection) {
        final String substring = "&_v".substring(1);
        if (collection != null) {
            for (final ef ef : collection) {
                if ("appendVersion".equals(ef.getId())) {
                    map.put(substring, ef.getValue());
                    break;
                }
            }
        }
    }
    
    private void cV() {
        final int n = this.cX() - this.vD + 1;
        if (n > 0) {
            final List<String> s = this.s(n);
            aa.y("Store full, deleting " + s.size() + " hits to make room.");
            this.a(s.toArray(new String[0]));
        }
    }
    
    static String w(final Map<String, String> map) {
        final ArrayList<String> list = new ArrayList<String>(map.size());
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            list.add(y.encode(entry.getKey()) + "=" + y.encode(entry.getValue()));
        }
        return TextUtils.join((CharSequence)"&", (Iterable)list);
    }
    
    @Override
    public void a(final Map<String, String> map, final long n, final String s, final Collection<ef> collection) {
        this.cW();
        this.cV();
        this.a(map, collection);
        this.a(map, n, s);
    }
    
    void a(final String[] array) {
        boolean b = true;
        if (array == null || array.length == 0) {
            aa.z("Empty hitIds passed to deleteHits.");
        }
        else {
            final SQLiteDatabase l = this.L("Error opening database for deleteHits.");
            if (l != null) {
                while (true) {
                    final String format = String.format("HIT_ID in (%s)", TextUtils.join((CharSequence)",", (Iterable)Collections.nCopies(array.length, "?")));
                    while (true) {
                        try {
                            l.delete("hits2", format, array);
                            final e so = this.sO;
                            if (this.cX() == 0) {
                                so.r(b);
                                return;
                            }
                        }
                        catch (SQLiteException ex) {
                            aa.z("Error deleting hits " + array);
                            return;
                        }
                        b = false;
                        continue;
                    }
                }
            }
        }
    }
    
    @Deprecated
    void b(final Collection<x> collection) {
        if (collection == null || collection.isEmpty()) {
            aa.z("Empty/Null collection passed to deleteHits.");
            return;
        }
        final String[] array = new String[collection.size()];
        final Iterator<x> iterator = collection.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            array[n] = String.valueOf(iterator.next().cP());
            ++n;
        }
        this.a(array);
    }
    
    @Override
    public void bW() {
        boolean b = true;
        aa.y("Dispatch running...");
        if (!this.vz.ch()) {
            return;
        }
        final List<x> t = this.t(40);
        if (t.isEmpty()) {
            aa.y("...nothing to dispatch");
            this.sO.r(true);
            return;
        }
        if (this.vB == null) {
            this.vB = new ab("_t=dispatch&_v=ma4.0.1", true);
        }
        if (this.cX() > t.size()) {
            b = false;
        }
        final int a = this.vz.a(t, this.vB, b);
        aa.y("sent " + a + " of " + t.size() + " hits");
        this.b(t.subList(0, Math.min(a, t.size())));
        if (a == t.size() && this.cX() > 0) {
            GoogleAnalytics.getInstance(this.mContext).dispatchLocalHits();
            return;
        }
        this.vB = null;
    }
    
    @Override
    public n bX() {
        return this.vz;
    }
    
    int cW() {
        boolean b = true;
        final long currentTimeMillis = this.tg.currentTimeMillis();
        if (currentTimeMillis > this.vC + 86400000L) {
            this.vC = currentTimeMillis;
            final SQLiteDatabase l = this.L("Error opening database for deleteStaleHits.");
            if (l != null) {
                final int delete = l.delete("hits2", "HIT_TIME < ?", new String[] { Long.toString(this.tg.currentTimeMillis() - 2592000000L) });
                final e so = this.sO;
                if (this.cX() != 0) {
                    b = false;
                }
                so.r(b);
                return delete;
            }
        }
        return 0;
    }
    
    int cX() {
        Cursor cursor = null;
        Cursor rawQuery = null;
        int n = 0;
        int n2 = 0;
        final SQLiteDatabase l = this.L("Error opening database for getNumStoredHits.");
        if (l == null) {
            return n2;
        }
        try {
            final Cursor cursor2 = cursor = (rawQuery = l.rawQuery("SELECT COUNT(*) from hits2", (String[])null));
            if (cursor2.moveToFirst()) {
                rawQuery = cursor2;
                cursor = cursor2;
                n = (int)cursor2.getLong(0);
            }
            n2 = n;
            return n;
        }
        catch (SQLiteException cursor) {
            cursor = rawQuery;
            aa.z("Error getting numStoredHits");
            return 0;
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    
    @Override
    public void j(final long n) {
        boolean b = true;
        final SQLiteDatabase l = this.L("Error opening database for clearHits");
        if (l != null) {
            if (n == 0L) {
                l.delete("hits2", (String)null, (String[])null);
            }
            else {
                l.delete("hits2", "hit_app_id = ?", new String[] { Long.valueOf(n).toString() });
            }
            final e so = this.sO;
            if (this.cX() != 0) {
                b = false;
            }
            so.r(b);
        }
    }
    
    List<String> s(final int p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/ArrayList.<init>:()V
        //     7: astore          6
        //     9: iload_1        
        //    10: ifgt            22
        //    13: ldc_w           "Invalid maxHits specified. Skipping"
        //    16: invokestatic    com/google/android/gms/analytics/aa.z:(Ljava/lang/String;)V
        //    19: aload           6
        //    21: areturn        
        //    22: aload_0        
        //    23: ldc_w           "Error opening database for peekHitIds."
        //    26: invokespecial   com/google/android/gms/analytics/ac.L:(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
        //    29: astore_3       
        //    30: aload_3        
        //    31: ifnonnull       37
        //    34: aload           6
        //    36: areturn        
        //    37: ldc_w           "%s ASC"
        //    40: iconst_1       
        //    41: anewarray       Ljava/lang/Object;
        //    44: dup            
        //    45: iconst_0       
        //    46: ldc             "hit_id"
        //    48: aastore        
        //    49: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //    52: astore          4
        //    54: iload_1        
        //    55: invokestatic    java/lang/Integer.toString:(I)Ljava/lang/String;
        //    58: astore          5
        //    60: aload_3        
        //    61: ldc             "hits2"
        //    63: iconst_1       
        //    64: anewarray       Ljava/lang/String;
        //    67: dup            
        //    68: iconst_0       
        //    69: ldc             "hit_id"
        //    71: aastore        
        //    72: aconst_null    
        //    73: aconst_null    
        //    74: aconst_null    
        //    75: aconst_null    
        //    76: aload           4
        //    78: aload           5
        //    80: invokevirtual   android/database/sqlite/SQLiteDatabase.query:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
        //    83: astore          4
        //    85: aload           4
        //    87: astore_3       
        //    88: aload           4
        //    90: invokeinterface android/database/Cursor.moveToFirst:()Z
        //    95: ifeq            135
        //    98: aload           4
        //   100: astore_3       
        //   101: aload           6
        //   103: aload           4
        //   105: iconst_0       
        //   106: invokeinterface android/database/Cursor.getLong:(I)J
        //   111: invokestatic    java/lang/String.valueOf:(J)Ljava/lang/String;
        //   114: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   119: pop            
        //   120: aload           4
        //   122: astore_3       
        //   123: aload           4
        //   125: invokeinterface android/database/Cursor.moveToNext:()Z
        //   130: istore_2       
        //   131: iload_2        
        //   132: ifne            98
        //   135: aload           4
        //   137: ifnull          147
        //   140: aload           4
        //   142: invokeinterface android/database/Cursor.close:()V
        //   147: aload           6
        //   149: areturn        
        //   150: astore          5
        //   152: aconst_null    
        //   153: astore          4
        //   155: aload           4
        //   157: astore_3       
        //   158: new             Ljava/lang/StringBuilder;
        //   161: dup            
        //   162: invokespecial   java/lang/StringBuilder.<init>:()V
        //   165: ldc_w           "Error in peekHits fetching hitIds: "
        //   168: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   171: aload           5
        //   173: invokevirtual   android/database/sqlite/SQLiteException.getMessage:()Ljava/lang/String;
        //   176: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   179: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   182: invokestatic    com/google/android/gms/analytics/aa.z:(Ljava/lang/String;)V
        //   185: aload           4
        //   187: ifnull          147
        //   190: aload           4
        //   192: invokeinterface android/database/Cursor.close:()V
        //   197: goto            147
        //   200: astore          4
        //   202: aconst_null    
        //   203: astore_3       
        //   204: aload_3        
        //   205: ifnull          214
        //   208: aload_3        
        //   209: invokeinterface android/database/Cursor.close:()V
        //   214: aload           4
        //   216: athrow         
        //   217: astore          4
        //   219: goto            204
        //   222: astore          5
        //   224: goto            155
        //    Signature:
        //  (I)Ljava/util/List<Ljava/lang/String;>;
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  37     85     150    155    Landroid/database/sqlite/SQLiteException;
        //  37     85     200    204    Any
        //  88     98     222    227    Landroid/database/sqlite/SQLiteException;
        //  88     98     217    222    Any
        //  101    120    222    227    Landroid/database/sqlite/SQLiteException;
        //  101    120    217    222    Any
        //  123    131    222    227    Landroid/database/sqlite/SQLiteException;
        //  123    131    217    222    Any
        //  158    185    217    222    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0098:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:138)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public List<x> t(final int p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: new             Ljava/util/ArrayList;
        //     3: dup            
        //     4: invokespecial   java/util/ArrayList.<init>:()V
        //     7: astore          5
        //     9: aload_0        
        //    10: ldc_w           "Error opening database for peekHits"
        //    13: invokespecial   com/google/android/gms/analytics/ac.L:(Ljava/lang/String;)Landroid/database/sqlite/SQLiteDatabase;
        //    16: astore          8
        //    18: aload           8
        //    20: ifnonnull       30
        //    23: aload           5
        //    25: astore          6
        //    27: aload           6
        //    29: areturn        
        //    30: aconst_null    
        //    31: astore          4
        //    33: ldc_w           "%s ASC"
        //    36: iconst_1       
        //    37: anewarray       Ljava/lang/Object;
        //    40: dup            
        //    41: iconst_0       
        //    42: ldc             "hit_id"
        //    44: aastore        
        //    45: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //    48: astore          6
        //    50: iload_1        
        //    51: invokestatic    java/lang/Integer.toString:(I)Ljava/lang/String;
        //    54: astore          7
        //    56: aload           8
        //    58: ldc             "hits2"
        //    60: iconst_2       
        //    61: anewarray       Ljava/lang/String;
        //    64: dup            
        //    65: iconst_0       
        //    66: ldc             "hit_id"
        //    68: aastore        
        //    69: dup            
        //    70: iconst_1       
        //    71: ldc             "hit_time"
        //    73: aastore        
        //    74: aconst_null    
        //    75: aconst_null    
        //    76: aconst_null    
        //    77: aconst_null    
        //    78: aload           6
        //    80: aload           7
        //    82: invokevirtual   android/database/sqlite/SQLiteDatabase.query:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
        //    85: astore          6
        //    87: aload           6
        //    89: astore          4
        //    91: new             Ljava/util/ArrayList;
        //    94: dup            
        //    95: invokespecial   java/util/ArrayList.<init>:()V
        //    98: astore          7
        //   100: aload           4
        //   102: invokeinterface android/database/Cursor.moveToFirst:()Z
        //   107: ifeq            154
        //   110: aload           7
        //   112: new             Lcom/google/android/gms/analytics/x;
        //   115: dup            
        //   116: aconst_null    
        //   117: aload           4
        //   119: iconst_0       
        //   120: invokeinterface android/database/Cursor.getLong:(I)J
        //   125: aload           4
        //   127: iconst_1       
        //   128: invokeinterface android/database/Cursor.getLong:(I)J
        //   133: invokespecial   com/google/android/gms/analytics/x.<init>:(Ljava/lang/String;JJ)V
        //   136: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   141: pop            
        //   142: aload           4
        //   144: invokeinterface android/database/Cursor.moveToNext:()Z
        //   149: istore_3       
        //   150: iload_3        
        //   151: ifne            110
        //   154: aload           4
        //   156: ifnull          166
        //   159: aload           4
        //   161: invokeinterface android/database/Cursor.close:()V
        //   166: aload           4
        //   168: astore          5
        //   170: ldc_w           "%s ASC"
        //   173: iconst_1       
        //   174: anewarray       Ljava/lang/Object;
        //   177: dup            
        //   178: iconst_0       
        //   179: ldc             "hit_id"
        //   181: aastore        
        //   182: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   185: astore          6
        //   187: aload           4
        //   189: astore          5
        //   191: iload_1        
        //   192: invokestatic    java/lang/Integer.toString:(I)Ljava/lang/String;
        //   195: astore          9
        //   197: aload           4
        //   199: astore          5
        //   201: aload           8
        //   203: ldc             "hits2"
        //   205: iconst_3       
        //   206: anewarray       Ljava/lang/String;
        //   209: dup            
        //   210: iconst_0       
        //   211: ldc             "hit_id"
        //   213: aastore        
        //   214: dup            
        //   215: iconst_1       
        //   216: ldc             "hit_string"
        //   218: aastore        
        //   219: dup            
        //   220: iconst_2       
        //   221: ldc             "hit_url"
        //   223: aastore        
        //   224: aconst_null    
        //   225: aconst_null    
        //   226: aconst_null    
        //   227: aconst_null    
        //   228: aload           6
        //   230: aload           9
        //   232: invokevirtual   android/database/sqlite/SQLiteDatabase.query:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
        //   235: astore          6
        //   237: aload           6
        //   239: invokeinterface android/database/Cursor.moveToFirst:()Z
        //   244: ifeq            319
        //   247: iconst_0       
        //   248: istore_1       
        //   249: aload           6
        //   251: checkcast       Landroid/database/sqlite/SQLiteCursor;
        //   254: invokevirtual   android/database/sqlite/SQLiteCursor.getWindow:()Landroid/database/CursorWindow;
        //   257: invokevirtual   android/database/CursorWindow.getNumRows:()I
        //   260: ifle            418
        //   263: aload           7
        //   265: iload_1        
        //   266: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   271: checkcast       Lcom/google/android/gms/analytics/x;
        //   274: aload           6
        //   276: iconst_1       
        //   277: invokeinterface android/database/Cursor.getString:(I)Ljava/lang/String;
        //   282: invokevirtual   com/google/android/gms/analytics/x.J:(Ljava/lang/String;)V
        //   285: aload           7
        //   287: iload_1        
        //   288: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   293: checkcast       Lcom/google/android/gms/analytics/x;
        //   296: aload           6
        //   298: iconst_2       
        //   299: invokeinterface android/database/Cursor.getString:(I)Ljava/lang/String;
        //   304: invokevirtual   com/google/android/gms/analytics/x.K:(Ljava/lang/String;)V
        //   307: aload           6
        //   309: invokeinterface android/database/Cursor.moveToNext:()Z
        //   314: istore_3       
        //   315: iload_3        
        //   316: ifne            691
        //   319: aload           6
        //   321: ifnull          331
        //   324: aload           6
        //   326: invokeinterface android/database/Cursor.close:()V
        //   331: aload           7
        //   333: areturn        
        //   334: astore          6
        //   336: aconst_null    
        //   337: astore          7
        //   339: aload           5
        //   341: astore          4
        //   343: aload           7
        //   345: astore          5
        //   347: new             Ljava/lang/StringBuilder;
        //   350: dup            
        //   351: invokespecial   java/lang/StringBuilder.<init>:()V
        //   354: ldc_w           "Error in peekHits fetching hitIds: "
        //   357: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   360: aload           6
        //   362: invokevirtual   android/database/sqlite/SQLiteException.getMessage:()Ljava/lang/String;
        //   365: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   368: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   371: invokestatic    com/google/android/gms/analytics/aa.z:(Ljava/lang/String;)V
        //   374: aload           4
        //   376: astore          6
        //   378: aload           5
        //   380: ifnull          27
        //   383: aload           5
        //   385: invokeinterface android/database/Cursor.close:()V
        //   390: aload           4
        //   392: areturn        
        //   393: astore          6
        //   395: aload           4
        //   397: astore          5
        //   399: aload           6
        //   401: astore          4
        //   403: aload           5
        //   405: ifnull          415
        //   408: aload           5
        //   410: invokeinterface android/database/Cursor.close:()V
        //   415: aload           4
        //   417: athrow         
        //   418: ldc_w           "HitString for hitId %d too large.  Hit will be deleted."
        //   421: iconst_1       
        //   422: anewarray       Ljava/lang/Object;
        //   425: dup            
        //   426: iconst_0       
        //   427: aload           7
        //   429: iload_1        
        //   430: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   435: checkcast       Lcom/google/android/gms/analytics/x;
        //   438: invokevirtual   com/google/android/gms/analytics/x.cP:()J
        //   441: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //   444: aastore        
        //   445: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   448: invokestatic    com/google/android/gms/analytics/aa.z:(Ljava/lang/String;)V
        //   451: goto            307
        //   454: astore          5
        //   456: aload           6
        //   458: astore          4
        //   460: aload           5
        //   462: astore          6
        //   464: aload           4
        //   466: astore          5
        //   468: new             Ljava/lang/StringBuilder;
        //   471: dup            
        //   472: invokespecial   java/lang/StringBuilder.<init>:()V
        //   475: ldc_w           "Error in peekHits fetching hitString: "
        //   478: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   481: aload           6
        //   483: invokevirtual   android/database/sqlite/SQLiteException.getMessage:()Ljava/lang/String;
        //   486: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   489: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   492: invokestatic    com/google/android/gms/analytics/aa.z:(Ljava/lang/String;)V
        //   495: aload           4
        //   497: astore          5
        //   499: new             Ljava/util/ArrayList;
        //   502: dup            
        //   503: invokespecial   java/util/ArrayList.<init>:()V
        //   506: astore          6
        //   508: iconst_0       
        //   509: istore_1       
        //   510: aload           4
        //   512: astore          5
        //   514: aload           7
        //   516: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   521: astore          7
        //   523: aload           4
        //   525: astore          5
        //   527: aload           7
        //   529: invokeinterface java/util/Iterator.hasNext:()Z
        //   534: ifeq            576
        //   537: aload           4
        //   539: astore          5
        //   541: aload           7
        //   543: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   548: checkcast       Lcom/google/android/gms/analytics/x;
        //   551: astore          8
        //   553: aload           4
        //   555: astore          5
        //   557: aload           8
        //   559: invokevirtual   com/google/android/gms/analytics/x.cO:()Ljava/lang/String;
        //   562: invokestatic    android/text/TextUtils.isEmpty:(Ljava/lang/CharSequence;)Z
        //   565: istore_3       
        //   566: iload_1        
        //   567: istore_2       
        //   568: iload_3        
        //   569: ifeq            593
        //   572: iload_1        
        //   573: ifeq            591
        //   576: aload           4
        //   578: ifnull          588
        //   581: aload           4
        //   583: invokeinterface android/database/Cursor.close:()V
        //   588: aload           6
        //   590: areturn        
        //   591: iconst_1       
        //   592: istore_2       
        //   593: aload           4
        //   595: astore          5
        //   597: aload           6
        //   599: aload           8
        //   601: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   606: pop            
        //   607: iload_2        
        //   608: istore_1       
        //   609: goto            523
        //   612: astore          4
        //   614: aload           5
        //   616: ifnull          626
        //   619: aload           5
        //   621: invokeinterface android/database/Cursor.close:()V
        //   626: aload           4
        //   628: athrow         
        //   629: astore          4
        //   631: aload           6
        //   633: astore          5
        //   635: goto            614
        //   638: astore          6
        //   640: goto            464
        //   643: astore          6
        //   645: aload           4
        //   647: astore          5
        //   649: aload           6
        //   651: astore          4
        //   653: goto            403
        //   656: astore          4
        //   658: goto            403
        //   661: astore          6
        //   663: aload           4
        //   665: astore          7
        //   667: aload           5
        //   669: astore          4
        //   671: aload           7
        //   673: astore          5
        //   675: goto            347
        //   678: astore          6
        //   680: aload           4
        //   682: astore          5
        //   684: aload           7
        //   686: astore          4
        //   688: goto            347
        //   691: iload_1        
        //   692: iconst_1       
        //   693: iadd           
        //   694: istore_1       
        //   695: goto            249
        //    Signature:
        //  (I)Ljava/util/List<Lcom/google/android/gms/analytics/x;>;
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  33     87     334    347    Landroid/database/sqlite/SQLiteException;
        //  33     87     393    403    Any
        //  91     100    661    678    Landroid/database/sqlite/SQLiteException;
        //  91     100    643    656    Any
        //  100    110    678    691    Landroid/database/sqlite/SQLiteException;
        //  100    110    643    656    Any
        //  110    150    678    691    Landroid/database/sqlite/SQLiteException;
        //  110    150    643    656    Any
        //  170    187    638    643    Landroid/database/sqlite/SQLiteException;
        //  170    187    612    614    Any
        //  191    197    638    643    Landroid/database/sqlite/SQLiteException;
        //  191    197    612    614    Any
        //  201    237    638    643    Landroid/database/sqlite/SQLiteException;
        //  201    237    612    614    Any
        //  237    247    454    464    Landroid/database/sqlite/SQLiteException;
        //  237    247    629    638    Any
        //  249    307    454    464    Landroid/database/sqlite/SQLiteException;
        //  249    307    629    638    Any
        //  307    315    454    464    Landroid/database/sqlite/SQLiteException;
        //  307    315    629    638    Any
        //  347    374    656    661    Any
        //  418    451    454    464    Landroid/database/sqlite/SQLiteException;
        //  418    451    629    638    Any
        //  468    495    612    614    Any
        //  499    508    612    614    Any
        //  514    523    612    614    Any
        //  527    537    612    614    Any
        //  541    553    612    614    Any
        //  557    566    612    614    Any
        //  597    607    612    614    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        //     at java.util.ArrayList.rangeCheck(ArrayList.java:653)
        //     at java.util.ArrayList.get(ArrayList.java:429)
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:3035)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:138)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    class a extends SQLiteOpenHelper
    {
        private boolean vF;
        private long vG;
        
        a(final Context context, final String s) {
            super(context, s, (SQLiteDatabase$CursorFactory)null, 1);
            this.vG = 0L;
        }
        
        private void a(final SQLiteDatabase sqLiteDatabase) {
            final boolean b = false;
            final Cursor rawQuery = sqLiteDatabase.rawQuery("SELECT * FROM hits2 WHERE 0", (String[])null);
            final HashSet<String> set = new HashSet<String>();
            try {
                final String[] columnNames = rawQuery.getColumnNames();
                for (int i = 0; i < columnNames.length; ++i) {
                    set.add(columnNames[i]);
                }
                rawQuery.close();
                if (!set.remove("hit_id") || !set.remove("hit_url") || !set.remove("hit_string") || !set.remove("hit_time")) {
                    throw new SQLiteException("Database column missing");
                }
            }
            finally {
                rawQuery.close();
            }
            boolean b2 = b;
            if (!set.remove("hit_app_id")) {
                b2 = true;
            }
            if (!set.isEmpty()) {
                throw new SQLiteException("Database has extra columns");
            }
            if (b2) {
                final SQLiteDatabase sqLiteDatabase2;
                sqLiteDatabase2.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id");
            }
        }
        
        private boolean a(final String p0, final SQLiteDatabase p1) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     0: aconst_null    
            //     1: astore          4
            //     3: aload_2        
            //     4: ldc             "SQLITE_MASTER"
            //     6: iconst_1       
            //     7: anewarray       Ljava/lang/String;
            //    10: dup            
            //    11: iconst_0       
            //    12: ldc             "name"
            //    14: aastore        
            //    15: ldc             "name=?"
            //    17: iconst_1       
            //    18: anewarray       Ljava/lang/String;
            //    21: dup            
            //    22: iconst_0       
            //    23: aload_1        
            //    24: aastore        
            //    25: aconst_null    
            //    26: aconst_null    
            //    27: aconst_null    
            //    28: invokevirtual   android/database/sqlite/SQLiteDatabase.query:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
            //    31: astore_2       
            //    32: aload_2        
            //    33: invokeinterface android/database/Cursor.moveToFirst:()Z
            //    38: istore_3       
            //    39: aload_2        
            //    40: ifnull          49
            //    43: aload_2        
            //    44: invokeinterface android/database/Cursor.close:()V
            //    49: iload_3        
            //    50: ireturn        
            //    51: astore_2       
            //    52: aconst_null    
            //    53: astore_2       
            //    54: new             Ljava/lang/StringBuilder;
            //    57: dup            
            //    58: invokespecial   java/lang/StringBuilder.<init>:()V
            //    61: ldc             "Error querying for table "
            //    63: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    66: aload_1        
            //    67: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //    70: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //    73: invokestatic    com/google/android/gms/analytics/aa.z:(Ljava/lang/String;)V
            //    76: aload_2        
            //    77: ifnull          86
            //    80: aload_2        
            //    81: invokeinterface android/database/Cursor.close:()V
            //    86: iconst_0       
            //    87: ireturn        
            //    88: astore_1       
            //    89: aload           4
            //    91: astore_2       
            //    92: aload_2        
            //    93: ifnull          102
            //    96: aload_2        
            //    97: invokeinterface android/database/Cursor.close:()V
            //   102: aload_1        
            //   103: athrow         
            //   104: astore_1       
            //   105: goto            92
            //   108: astore_1       
            //   109: goto            92
            //   112: astore          4
            //   114: goto            54
            //    Exceptions:
            //  Try           Handler
            //  Start  End    Start  End    Type                                     
            //  -----  -----  -----  -----  -----------------------------------------
            //  3      32     51     54     Landroid/database/sqlite/SQLiteException;
            //  3      32     88     92     Any
            //  32     39     112    117    Landroid/database/sqlite/SQLiteException;
            //  32     39     104    108    Any
            //  54     76     108    112    Any
            // 
            // The error that occurred was:
            // 
            // java.lang.IndexOutOfBoundsException: Index: 64, Size: 64
            //     at java.util.ArrayList.rangeCheck(ArrayList.java:653)
            //     at java.util.ArrayList.get(ArrayList.java:429)
            //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3303)
            //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:113)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
            //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:556)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
            //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
            //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
            //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
            //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:138)
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        public SQLiteDatabase getWritableDatabase() {
            if (this.vF && this.vG + 3600000L > ac.this.tg.currentTimeMillis()) {
                throw new SQLiteException("Database creation failed");
            }
            SQLiteDatabase writableDatabase = null;
            this.vF = true;
            this.vG = ac.this.tg.currentTimeMillis();
            while (true) {
                try {
                    writableDatabase = super.getWritableDatabase();
                    SQLiteDatabase writableDatabase2 = writableDatabase;
                    if (writableDatabase == null) {
                        writableDatabase2 = super.getWritableDatabase();
                    }
                    this.vF = false;
                    return writableDatabase2;
                }
                catch (SQLiteException ex) {
                    ac.this.mContext.getDatabasePath(ac.this.vA).delete();
                    continue;
                }
                break;
            }
        }
        
        public void onCreate(final SQLiteDatabase sqLiteDatabase) {
            p.G(sqLiteDatabase.getPath());
        }
        
        public void onOpen(final SQLiteDatabase sqLiteDatabase) {
            while (true) {
                if (Build$VERSION.SDK_INT < 15) {
                    final Cursor rawQuery = sqLiteDatabase.rawQuery("PRAGMA journal_mode=memory", (String[])null);
                    try {
                        rawQuery.moveToFirst();
                        rawQuery.close();
                        if (!this.a("hits2", sqLiteDatabase)) {
                            sqLiteDatabase.execSQL(ac.vx);
                            return;
                        }
                    }
                    finally {
                        rawQuery.close();
                    }
                    final SQLiteDatabase sqLiteDatabase2;
                    this.a(sqLiteDatabase2);
                    return;
                }
                continue;
            }
        }
        
        public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int n, final int n2) {
        }
    }
}