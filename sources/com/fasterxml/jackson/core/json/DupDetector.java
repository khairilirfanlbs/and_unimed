package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import java.util.HashSet;

public class DupDetector {
    protected String _firstName;
    protected String _secondName;
    protected HashSet<String> _seen;
    protected final Object _source;

    private DupDetector(Object obj) {
        this._source = obj;
    }

    public static DupDetector rootDetector(JsonGenerator jsonGenerator) {
        return new DupDetector(jsonGenerator);
    }

    public static DupDetector rootDetector(JsonParser jsonParser) {
        return new DupDetector(jsonParser);
    }

    public DupDetector child() {
        return new DupDetector(this._source);
    }

    public Object getSource() {
        return this._source;
    }

    public boolean isDup(String str) {
        if (this._firstName == null) {
            this._firstName = str;
            return false;
        } else if (str.equals(this._firstName)) {
            return true;
        } else {
            if (this._secondName == null) {
                this._secondName = str;
                return false;
            } else if (str.equals(this._secondName)) {
                return true;
            } else {
                if (this._seen == null) {
                    this._seen = new HashSet(16);
                    this._seen.add(this._firstName);
                    this._seen.add(this._secondName);
                }
                return !this._seen.add(str);
            }
        }
    }

    public void reset() {
        this._firstName = null;
        this._secondName = null;
        this._seen = null;
    }
}
