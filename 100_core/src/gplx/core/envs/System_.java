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
public class System_ {
	// *** ticks
	public static long	Ticks() {return Ticks__test_val >= 0 ? Ticks__test_val : System.currentTimeMillis();} 
	public static int	Ticks__elapsed_in_sec	(long time_bgn) {return (int)(Ticks() - time_bgn) / 1000;}
	public static int	Ticks__elapsed_in_frac	(long time_bgn) {return (int)(Ticks() - time_bgn);}
	public static void	Ticks__test_set(long v) {Ticks__test_val = v;}
	public static void	Ticks__test_add(long v) {Ticks__test_val += v;}
	public static void	Ticks__test_off()		{Ticks__test_val = -1;}
	private static long	Ticks__test_val = -1;	// in milliseconds

	// *** misc methods: Exit / GC
	public static void Exit() {Exit_by_code(0);}
	public static void Exit_by_code(int code) {System.exit(code);}	
	public static void Garbage_collect() {if (Env_.Mode_testing()) return; System.gc();}				

	// *** java properties: getProperty; "-D" properties
	public static String Prop__user_language()	{return Prop__get(Prop_key__user_language);}
	public static String Prop__user_name()		{return Prop__get(Prop_key__user_name);}	
	public static String Prop__java_version()	{return Prop__get(Prop_key__java_version);}
	public static String Prop__java_home()		{return Prop__get(Prop_key__java_home);}
	private static String Prop__get(String key) {
				return System.getProperty(key);
			}
	private static final String
	  Prop_key__user_language	= "user.language"
	, Prop_key__user_name		= "user.name"
	, Prop_key__java_version	= "java.version"
	, Prop_key__java_home		= "java.version"
	;

	// *** environment variables: getenv
	public static String Env__machine_name() {
		String rv = "";
		rv = Env__get(Env_key__computername);	if (String_.Len_gt_0(rv)) return rv;
		rv = Env__get(Env_key__hostname);		if (String_.Len_gt_0(rv)) return rv;
		return "UNKNOWN_MACHINE_NAME";
	}
	private static String Env__get(String key) {
				return System.getenv(key);
			}
	private static final String
	  Env_key__computername = "COMPUTERNAME"
	, Env_key__hostname = "HOSTNAME"
	;
}
