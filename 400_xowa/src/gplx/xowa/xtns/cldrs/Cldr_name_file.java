/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.cldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Cldr_name_file {
	public Cldr_name_file(String key) {
		this.key = key;
	}
	public String Key() {return key;} private final String key;
	public Ordered_hash Language_names() {return language_names;} private final Ordered_hash language_names = Ordered_hash_.New();
	public Ordered_hash Currency_names() {return currency_names;} private final Ordered_hash currency_names = Ordered_hash_.New();
	public Ordered_hash Currency_symbols() {return currency_symbols;} private final Ordered_hash currency_symbols = Ordered_hash_.New();
	public Ordered_hash Country_names() {return country_names;} private final Ordered_hash country_names = Ordered_hash_.New();
	public Ordered_hash Time_units() {return time_units;} private final Ordered_hash time_units = Ordered_hash_.New();

	public static final Cldr_name_file Empty = new Cldr_name_file("EMPTY");
}
