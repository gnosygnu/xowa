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
package gplx.xowa.htmls.core.wkrs.mkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.threads.poolables.*;
import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.htmls.core.wkrs.escapes.*; import gplx.xowa.htmls.core.wkrs.spaces.*;
import gplx.xowa.htmls.core.wkrs.hdrs.*; import gplx.xowa.htmls.core.wkrs.lnkes.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import gplx.xowa.htmls.core.wkrs.imgs.*; import gplx.xowa.htmls.core.wkrs.thms.*;
public class Xoh_hdoc_mkr {
	private Gfo_poolable_mgr
	  pool__escape__hzip	= Gfo_poolable_mgr_.New(1, 32, new Xoh_escape_hzip())
	, pool__space__hzip		= Gfo_poolable_mgr_.New(1, 32, new Xoh_space_hzip())
	, pool__hdr__hzip		= Gfo_poolable_mgr_.New(1, 32, new Xoh_hdr_hzip())
	, pool__lnke__hzip		= Gfo_poolable_mgr_.New(1, 32, new Xoh_lnke_hzip())
	, pool__lnki__hzip		= Gfo_poolable_mgr_.New(1, 32, new Xoh_lnki_hzip())
	, pool__img__hzip		= Gfo_poolable_mgr_.New(1, 32, new Xoh_img_hzip())
	, pool__thm__hzip		= Gfo_poolable_mgr_.New(1, 32, new Xoh_thm_hzip())
	;
	public Xoh_hzip_wkr Hzip__wkr(byte tid) {
		switch (tid) {
			case Xoh_hzip_dict_.Tid__escape:		return Escape__hzip();
			case Xoh_hzip_dict_.Tid__space:			return Space__hzip();
			case Xoh_hzip_dict_.Tid__hdr:			return Hdr__hzip();
			case Xoh_hzip_dict_.Tid__lnke:			return Lnke__hzip();
			case Xoh_hzip_dict_.Tid__lnki:			return Lnki__hzip();
			case Xoh_hzip_dict_.Tid__img:			return Img__hzip();
			case Xoh_hzip_dict_.Tid__thm:			return Thm__hzip();
			default:								throw Err_.new_unhandled(tid);
		}
	}
	public Xoh_escape_hzip		Escape__hzip()		{return (Xoh_escape_hzip)		pool__escape__hzip.Get_fast();}
	public Xoh_space_hzip		Space__hzip()		{return (Xoh_space_hzip)		pool__space__hzip.Get_fast();}
	public Xoh_hdr_hzip			Hdr__hzip()			{return (Xoh_hdr_hzip)			pool__hdr__hzip.Get_fast();}
	public Xoh_lnke_hzip		Lnke__hzip()		{return (Xoh_lnke_hzip)			pool__lnke__hzip.Get_fast();}
	public Xoh_lnki_hzip		Lnki__hzip()		{return (Xoh_lnki_hzip)			pool__lnki__hzip.Get_fast();}
	public Xoh_img_hzip			Img__hzip()			{return (Xoh_img_hzip)			pool__img__hzip.Get_fast();}
	public Xoh_thm_hzip			Thm__hzip()			{return (Xoh_thm_hzip)			pool__thm__hzip.Get_fast();}
}
