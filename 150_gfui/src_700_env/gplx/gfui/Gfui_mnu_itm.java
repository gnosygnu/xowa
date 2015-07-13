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
package gplx.gfui; import gplx.*;
public interface Gfui_mnu_itm {
	int Tid();
	String Uid();
	boolean Enabled(); void Enabled_(boolean v);
	String Text(); void Text_(String v);
	ImageAdp Img(); void Img_(ImageAdp v);
	boolean Selected(); void Selected_(boolean v);
	Object Under();
}
class Gfui_mnu_itm_null implements Gfui_mnu_itm {
	public String Uid() {return "";}
	public int Tid() {return Gfui_mnu_itm_.Tid_btn;}
	public boolean Enabled() {return true;} public void Enabled_(boolean v) {}
	public String Text() {return text;} public void Text_(String v) {text = v;} private String text;
	public ImageAdp Img() {return img;} public void Img_(ImageAdp v) {img = v;} private ImageAdp img;
	public boolean Selected() {return true;} public void Selected_(boolean v) {}
	public Object Under() {return null;}
	public static final Gfui_mnu_itm_null Null = new Gfui_mnu_itm_null(); Gfui_mnu_itm_null() {}
}
