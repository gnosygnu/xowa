/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.ios; import gplx.*;
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
		List_adp list = List_adp_.new_();
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
	public static final IoRecycleBin _ = new IoRecycleBin(); IoRecycleBin() {}
}
