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
package gplx.xowa.guis.views.boots; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.views.*;
import gplx.core.envs.*;
public class Xog_error_data {
	public Xog_error_data(String full_msg, String err_details, String err_msg) {
		this.full_msg = full_msg;
		this.err_details = err_details;
		this.err_msg = err_msg;
	}
	public String Full_msg() {return full_msg;} private final    String full_msg;
	public String Err_details() {return err_details;} private final    String err_details;
	public String Err_msg() {return err_msg;} private final    String err_msg;
	public static Xog_error_data new_(String err_msg, String err_trace) {
		String err_details = String_.Concat_lines_nl_skip_last
		( "OS: "		+ Op_sys.Cur().Os_name()
		, "Java: "		+ System_.Prop__java_version() + " (" + Op_sys.Cur().Bitness_str() + " bit)"
		, "Java path: " + System_.Prop__java_home()
		, "XOWA: "		+ Xoa_app_.Version
		, "XOWA path: " + Env_.AppUrl().Raw()
		, ""
		, "Error: "		+ err_msg
		, "Stack: "		+ err_trace
		);
		String advice = Make_advice(err_msg);
		String full_msg = String_.Concat_lines_nl_skip_last
		( "Sorry! XOWA failed to run!"
		, ""
		, advice
		, ""
		, "You can also open an issue or send an email with the data below."
		, ""
		, "Thanks!"
		, ""
		, "----"
		, err_details
		);
		return new Xog_error_data(full_msg, err_details, err_msg);
	}
	private static String Make_advice(String err_msg) {
		String check_troubleshooting_section = "check the TROUBLESHOOTING section in the readme.txt for known issues.";
		if (String_.Has(err_msg, "Cannot load 64-bit SWT libraries on 32-bit JVM"))
			return String_.Concat_lines_nl_skip_last
			( "Your Java installation looks like it's 32-bit. Please use the 32-bit package of XOWA."
			, ""
			, "For example, if you downloaded xowa_app_windows_64_v2.10.1.1.zip (64-bit), download xowa_app_windows_v2.10.1.1.zip (32-bit)"
			, ""
			, "You may also want to " + check_troubleshooting_section
			);
		else
			return "Please " + check_troubleshooting_section;
	}
}
