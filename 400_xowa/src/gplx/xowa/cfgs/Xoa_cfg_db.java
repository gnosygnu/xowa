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
package gplx.xowa.cfgs; import gplx.*; import gplx.xowa.*;
public interface Xoa_cfg_db {
	void Cfg_reset_all(Xoa_cfg_mgr cfg_mgr);
	void Cfg_save_bgn(Xoa_cfg_mgr cfg_mgr);
	void Cfg_save_end(Xoa_cfg_mgr cfg_mgr);
	void Cfg_save_run(Xoa_cfg_mgr cfg_mgr, Xoa_cfg_grp cfg_grp, Xoa_cfg_itm cfg_itm);
	void Cfg_load_run(Xoa_cfg_mgr cfg_mgr);
}
