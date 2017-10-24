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
package gplx.core.envs; import gplx.*; import gplx.core.*;
public class Runtime_ {
	// *** Hardware-related
	public static int  Cpu_count() {return Runtime.getRuntime().availableProcessors();}		
	public static long Memory_max() {return Runtime.getRuntime().maxMemory();}		
	public static long Memory_total() {return Runtime.getRuntime().totalMemory();}	
	public static long Memory_free() {return Runtime.getRuntime().freeMemory();}	
	public static long Memory_used() {return Memory_total() - Memory_free();}	// REF:http://stackoverflow.com/questions/3571203/what-are-runtime-getruntime-totalmemory-and-freememory

	public static void Exec(String v) {
				try {
			Runtime.getRuntime().exec(v);
		} catch (Exception e) {
			Gfo_usr_dlg_.Instance.Log_many("", "", "runtime exec failed; err=~{0}", Err_.Message_gplx_log(e));
		}
			}
}