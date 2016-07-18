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
package gplx.core.brys; import gplx.*; import gplx.core.*;
public class Bry_bfr_mkr {		
	public Bry_bfr Get_b128() {return mkr_b128.Get();} private final    Bry_bfr_mkr_mgr mkr_b128 = new Bry_bfr_mkr_mgr(Tid_b128, 128);
	public Bry_bfr Get_b512() {return mkr_b512.Get();} private final    Bry_bfr_mkr_mgr mkr_b512 = new Bry_bfr_mkr_mgr(Tid_b512, 512);
	public Bry_bfr Get_k004() {return mkr_k004.Get();} private final    Bry_bfr_mkr_mgr mkr_k004 = new Bry_bfr_mkr_mgr(Tid_k004, 4 * Io_mgr.Len_kb);
	public Bry_bfr Get_m001() {return mkr_m001.Get();} private final    Bry_bfr_mkr_mgr mkr_m001 = new Bry_bfr_mkr_mgr(Tid_m001, 1 * Io_mgr.Len_mb); 
	public void Clear() {
		for (byte i = Tid_b128; i <= Tid_m001; i++)
			mkr(i).Clear();
	}
	public void Clear_fail_check() {
		for (byte i = Tid_b128; i <= Tid_m001; i++)
			mkr(i).Clear_fail_check();
	}
	private Bry_bfr_mkr_mgr mkr(byte tid) {
		switch (tid) {
			case Tid_b128: 	return mkr_b128;
			case Tid_b512: 	return mkr_b512;
			case Tid_k004: 	return mkr_k004;
			case Tid_m001: 	return mkr_m001;
			default:		throw Err_.new_unhandled(tid);
		}
	}
	public static final byte Tid_b128 = 0, Tid_b512 = 1, Tid_k004 = 2, Tid_m001 = 3;
}
