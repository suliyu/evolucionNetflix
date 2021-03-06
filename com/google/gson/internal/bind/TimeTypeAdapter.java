// 
// Decompiled by Procyon v0.5.30
// 

package com.google.gson.internal.bind;

import java.util.Date;
import com.google.gson.stream.JsonWriter;
import java.text.ParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonReader;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import com.google.gson.TypeAdapterFactory;
import java.sql.Time;
import com.google.gson.TypeAdapter;

public final class TimeTypeAdapter extends TypeAdapter<Time>
{
    public static final TypeAdapterFactory FACTORY;
    private final DateFormat format;
    
    static {
        FACTORY = new TimeTypeAdapter$1();
    }
    
    public TimeTypeAdapter() {
        this.format = new SimpleDateFormat("hh:mm:ss a");
    }
    
    @Override
    public Time read(final JsonReader jsonReader) {
        synchronized (this) {
            Time time;
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                time = null;
            }
            else {
                try {
                    time = new Time(this.format.parse(jsonReader.nextString()).getTime());
                }
                catch (ParseException ex) {
                    throw new JsonSyntaxException(ex);
                }
            }
            return time;
        }
    }
    
    @Override
    public void write(final JsonWriter jsonWriter, final Time time) {
        // monitorenter(this)
        Label_0017: {
            if (time != null) {
                break Label_0017;
            }
            String format = null;
            try {
                while (true) {
                    jsonWriter.value(format);
                    return;
                    format = this.format.format(time);
                    continue;
                }
            }
            finally {
            }
            // monitorexit(this)
        }
    }
}
