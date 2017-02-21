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
package gplx.xowa.htmls.core.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
public class Xoh_html_wtr_cfg {
	public boolean Toc__show()			{return toc__show;} public Xoh_html_wtr_cfg Toc__show_(boolean v) {toc__show = v; return this;} private boolean toc__show;
	public boolean Lnki__id()			{return lnki__id;} public Xoh_html_wtr_cfg Lnki__id_(boolean v) {lnki__id = v; return this;} private boolean lnki__id;
	public boolean Lnki__title()		{return lnki__title;} public Xoh_html_wtr_cfg Lnki__title_(boolean v)	{lnki__title = v; return this;} private boolean lnki__title;
	public boolean Lnki__visited()		{return lnki__visited;} public Xoh_html_wtr_cfg Lnki__visited_y_() {lnki__visited = true; return this;} private boolean lnki__visited;
}
