package com.asstnavi.generic.Enum;

public enum MatchMode {
    STARTS_WITH("startsWith"),
    NOT_STARTS_WITH("notStartsWith"),
    ENDS_WITH("endsWith"),
    NOT_ENDS_WITH("notEndsWith"),
    CONTAINS("contains"),
    NOT_CONTAINS("notContains"),
    EXACT("exact"),
    NOT_EXACT("notExact"),
    LESS_THAN("lt"),
    LESS_THAN_EQUALS("lte"),
    GREATER_THAN("gt"),
    GREATER_THAN_EQUALS("gte"),
    EQUALS("equals"),
    NOT_EQUALS("notEquals"),
    IN("in"),
    NOT_IN("notIn"),
    BETWEEN("between"),
    NOT_BETWEEN("notBetween"),
    GLOBAL("global"),
    /** @deprecated */
    @Deprecated
    RANGE("range");

    private final String operator;

    private MatchMode(String operator) {
        this.operator = operator;
    }

    public String operator() {
        return this.operator;
    }

    public static MatchMode of(String operator) {
        MatchMode[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            MatchMode mode = var1[var3];
            if (mode.operator().equals(operator)) {
                return mode;
            }
        }

        return null;
    }
}
