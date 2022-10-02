package com.asstnavi.generic.LazyPack;

public class LazyToSql
{
    public String[] tableSqlCommand(String filterTemp, String globalFilterCommand, String sortTemp, boolean sortByIsEmpty,LazyService service) {
        //fieldfilterda kalan son and için
        if (filterTemp.length() > 4 && filterTemp.substring(filterTemp.length() - 4).equals("And "))
            filterTemp = filterTemp.substring(0, filterTemp.length() - 4);
        if (globalFilterCommand.length() > 0) {
            // globalfilter ve field filters arasındaki AND YAZISI İÇİN fieldfilters+) and (+globalfilters
            if (filterTemp.length() < 6 && globalFilterCommand.length() > 4) {
                globalFilterCommand = globalFilterCommand.substring(4);
            }
            filterTemp += globalFilterCommand + " ";
        }
        //eğer fitlertemp dolu ise where komutu ile arama yapılır

        if (service.isFilter() && filterTemp.substring(filterTemp.length() - 5).toLowerCase().contains("and"))
            filterTemp = filterTemp.substring(0, filterTemp.length() - 5);
        if (service.isFilter()) {
            filterTemp = "  Where  (" + filterTemp + ")";
        } /*else
           ///statu olayı istenirse eklenebilir
            filterTemp = " Where statu=1 ";*/

        String noOrderBy = filterTemp;

        if (!sortByIsEmpty) {
            filterTemp += " " + sortTemp;

        }

        return new String[]{filterTemp, noOrderBy};
    }
}
