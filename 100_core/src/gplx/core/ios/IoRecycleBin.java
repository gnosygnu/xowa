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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.core.strings.*;
public class IoRecycleBin {
	public void							Send(Io_url url) {Send_xrg(url).Exec();}
	public IoEngine_xrg_recycleFil			Send_xrg(Io_url url) {return IoEngine_xrg_recycleFil.gplx_(url);}
	public void							Recover(Io_url url) {
		String_bldr sb = String_bldr_.new_();
		List_adp list = Regy_search(url, sb);
		int listCount = list.Count(); if (listCount > 1) throw Err_.new_wo_type("found more than 1 url", "count", list.Count());
		Io_url trgUrl = (Io_url)list.Get_at(0);
		IoEngine_xrg_xferFil.move_(url, trgUrl).ReadOnlyFails_(true).Overwrite_(false).Exec();
		IoEngine_xrg_saveFilStr.new_(FetchRegistryUrl(url), sb.To_str()).Exec();
	}
	public void Regy_add(IoEngine_xrg_recycleFil xrg) {
		Io_url url = xrg.RecycleUrl();
		Io_url regyUrl = FetchRegistryUrl(url);
		String text = String_.Concat_with_obj("|", url.NameAndExt_noDirSpr(), xrg.Url().GenRelUrl_orEmpty(url.OwnerRoot()), xrg.Uuid().To_str(), xrg.AppName(), xrg.Time());
		IoEngine_xrg_saveFilStr.new_(regyUrl, text).Append_().Exec();
	}
	public List_adp Regy_search(Io_url url, String_bldr sb) {
		List_adp list = List_adp_.New();
		Io_url regyUrl = FetchRegistryUrl(url);
		String[] lines = IoEngine_xrg_loadFilStr.new_(regyUrl).ExecAsStrAry();
		int linesLen = Array_.Len(lines); 
		String nameAndExt = url.NameAndExt_noDirSpr() + "|";
		for (int i = linesLen; i > 0; i--) {
			String line = lines[i - 1];
			if (String_.Has_at_bgn(line, nameAndExt)) {
				String[] terms = String_.Split(line, "|");
				Io_url origUrl = url.OwnerRoot().GenSubFil(terms[1]);
				list.Add(origUrl);
			}
			else
				sb.Add_str_w_crlf(line);
		}
		return list;
	}
	Io_url FetchRegistryUrl(Io_url url) {
		String sourceApp = String_.GetStrBefore(url.NameAndExt_noDirSpr(), ";");
		return url.OwnerDir().GenSubFil_ary(sourceApp, ".recycle.csv");
	}
	public static final    IoRecycleBin Instance = new IoRecycleBin(); IoRecycleBin() {}
}
