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
package gplx.core.envs; import gplx.*; import gplx.core.*;
public class Runtime_ {
	// *** Hardware-related
	public static int  Cpu_count() {return Runtime.getRuntime().availableProcessors();}		
	public static long Memory_max() {return Runtime.getRuntime().maxMemory();}		
	public static long Memory_total() {return Runtime.getRuntime().totalMemory();}	
	public static long Memory_free() {return Runtime.getRuntime().freeMemory();}	

	public static void Exec(String v) {
				try {
			Runtime.getRuntime().exec(v);
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Log_many("", "", "runtime exec failed; err=~{0}", Err_.Message_gplx_log(e));
		}
			}
}