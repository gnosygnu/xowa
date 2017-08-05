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
package gplx.xowa.apps.boots; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.core.consoles.*; import gplx.core.envs.*;
import gplx.xowa.apps.*;
public class Xoa_cmd_arg_mgr {
	Xoa_cmd_arg_mgr(Gfo_cmd_arg_mgr arg_mgr) {this.arg_mgr = arg_mgr;}
	public Gfo_cmd_arg_mgr Arg_mgr() {return arg_mgr;} private final    Gfo_cmd_arg_mgr arg_mgr;
	public Xoa_app_mode App_type() {return app_type;} private Xoa_app_mode app_type;
	public Io_url Fsys__root_dir() {return fsys__root_dir;} private Io_url fsys__root_dir;
	public String Fsys__bin_dir() {return fsys__bin_dir;} private String fsys__bin_dir;
	public Io_url Fsys__user_dir() {return fsys__user_dir;} private Io_url fsys__user_dir;
	public Io_url Fsys__wiki_dir() {return fsys__wiki_dir;} private Io_url fsys__wiki_dir;
	public Io_url Cmd__file() {return cmd__file;} private Io_url cmd__file;
	public String Cmd__text() {return cmd__text;} private String cmd__text;
	public int Tcp__port_recv() {return tcp__port_recv;} private int tcp__port_recv;
	public int Tcp__port_send() {return tcp__port_send;} private int tcp__port_send;
	public int Http__port() {return http__port;} private int http__port;
	public String Http__home_page() {return http__home_page;} private String http__home_page;
	public int Http__max_clients() {return http__max_clients;} private int http__max_clients;
	public int Http__max_clients_timeout() {return http__max_clients_timeout;} private int http__max_clients_timeout;
	public String Http__special_pages_safelist() {return http__special_pages_safelist;} private String http__special_pages_safelist;
	public String Gui__home_page() {return gui__home_page;} private String gui__home_page;
	public boolean Process(Gfo_usr_dlg usr_dlg, String[] args, Io_url jar_dir) {
		arg_mgr.Parse(args);
		if (!Print(usr_dlg)) return false;
		this.app_type = Xoa_app_mode.parse(arg_mgr.Get_by("app_mode").Val_as_str_or("gui"));
		this.fsys__root_dir = arg_mgr.Get_by("root_dir").Val_as_url__rel_dir_or(jar_dir, jar_dir);
		this.fsys__user_dir = arg_mgr.Get_by("user_dir").Val_as_url__rel_dir_or(fsys__root_dir.GenSubDir("user"), fsys__root_dir.GenSubDir_nest("user", User_name_default));
		this.fsys__wiki_dir = arg_mgr.Get_by("wiki_dir").Val_as_url__rel_dir_or(fsys__root_dir.GenSubDir("wiki"), fsys__root_dir.GenSubDir("wiki"));
		this.cmd__file = arg_mgr.Get_by("cmd_file").Val_as_url__rel_fil_or(jar_dir, fsys__root_dir.GenSubFil_nest("bin", "any", "xowa", "cfg" ,"app", "xowa.gfs"));
		this.cmd__text = arg_mgr.Get_by("cmd_text").Val_as_str_or(null);
		this.tcp__port_recv = arg_mgr.Get_by("server_port_recv").Val_as_int_or(55000);
		this.tcp__port_send = arg_mgr.Get_by("server_port_send").Val_as_int_or(55001);
		this.http__port = arg_mgr.Get_by("http_server_port").Val_as_int_or(8080);
		this.http__home_page = arg_mgr.Get_by("http_server_home").Val_as_str_or("home/wiki/Main_Page");
		this.http__max_clients = arg_mgr.Get_by("http_server.max_clients").Val_as_int_or(0);
		this.http__max_clients_timeout = arg_mgr.Get_by("http_server.max_clients_timeout").Val_as_int_or(50);
		this.http__special_pages_safelist = arg_mgr.Get_by("http_server.special_pages_safelist").Val_as_str_or(null);
		this.gui__home_page = arg_mgr.Get_by("url").Val_as_str_or(null);
		this.fsys__bin_dir = arg_mgr.Get_by("bin_dir_name").Val_as_str_or(Bin_dir_name());
		return true;
	}
	private boolean Print(Gfo_usr_dlg usr_dlg) {
		String header = String_.Concat_lines_nl_skip_last
		(	Xoa_cmd_arg_mgr_.GenHdr(false, "XOWA", "XOWA: the XOWA Offline Wiki Application\n", "")
		,	String_.Repeat("-", 80) 
		,	""
		,	"version: " + Xoa_app_.Version + "; build date: " + Xoa_app_.Build_date
		);
		Gfo_cmd_arg_mgr_printer printer = new Gfo_cmd_arg_mgr_printer(arg_mgr);
		return printer.Print(usr_dlg, header, Xoa_app_.Name, "help", "show_license", "show_args");
	}
	public static Xoa_cmd_arg_mgr new_() {
		Gfo_cmd_arg_mgr arg_mgr = new Gfo_cmd_arg_mgr().Reg_many
		( Gfo_cmd_arg_itm_.opt_("root_dir").Example_url_("C:\\xowa").Note_("root directory for xowa; defaults to current directory of xowa.jar")
		, Gfo_cmd_arg_itm_.opt_("user_dir").Example_url_("C:\\xowa\\user\\" + User_name_default).Note_("directory for user_data; defaults to '/xowa/user/" + User_name_default + "'")
		, Gfo_cmd_arg_itm_.opt_("wiki_dir").Example_url_("C:\\xowa\\wiki\\").Note_("directory for wikis; defaults to '/xowa/wiki/'")
		, Gfo_cmd_arg_itm_.opt_("bin_dir_name").Example_("windows").Note_("platform-dependent directory name inside /xowa/bin/; valid values are 'linux', 'macosx', 'windows', 'linux_64', 'macosx_64', 'windows_64'; defaults to detected version")
		, Gfo_cmd_arg_itm_.opt_("app_mode").Example_("gui").Note_("type of app to run; valid values are 'gui', 'cmd', 'server', 'http_server'; defaults to 'gui'")
		, Gfo_cmd_arg_itm_.opt_("cmd_file").Example_url_("C:\\xowa\\bin\\any\\xowa\\cfg\\app\\xowa.gfs").Note_("file_path of script to execute; defaults to 'xowa.gfs'")
		, Gfo_cmd_arg_itm_.opt_("cmd_text").Example_("\"app.shell.fetch_page('en.wikipedia.org/wiki/Earth', 'html');\"").Note_("script to run; runs after cmd_file; does nothing if empty; default is empty.\nCurrently a useful cmd is to do 'java -jar xowa_your_platform.jar --app_mode cmd --show_license n --show_args n --cmd_text \"app.shell.fetch_page('en.wikipedia.org/wiki/Earth' 'html');\"'. This will output the page's html to the console. You can also change 'html' to 'wiki' to get the wikitext.")
		, Gfo_cmd_arg_itm_.opt_("url").Example_("en.wikipedia.org/wiki/Earth").Note_("url to be shown when xowa first launches; default is home/wiki/Main_Page")
		, Gfo_cmd_arg_itm_.opt_("server_port_recv").Example_("55000").Note_("applies to --app_mode server; port where xowa server will receive messages; clients should send messages to this port")
		, Gfo_cmd_arg_itm_.opt_("server_port_send").Example_("55001").Note_("applies to --app_mode server; port where xowa server will send messages; clients should listen for messages from this port")
		, Gfo_cmd_arg_itm_.opt_("http_server_port").Example_("8080").Note_("applies to --app_mode http_server; port used by http_server; default is 8080")
		, Gfo_cmd_arg_itm_.opt_("http_server_home").Example_("home/wiki/Main_Page").Note_("applies to --app_mode http_server; default home page for root address. EX: navigating to localhost:8080 will navigate to localhost:8080/home/wiki/Main_Page. To navigate to a wiki's main page, use the domain name only. EX: --http_server_home en.wikipedia.org")
		, Gfo_cmd_arg_itm_.opt_("http_server.max_clients").Example_("15").Note_("applies to --app_mode http_server; limits maximum number of concurrent connections; default is 0 (no limit)")
		, Gfo_cmd_arg_itm_.opt_("http_server.max_clients_timeout").Example_("50").Note_("applies to --app_mode http_server; time in milliseconds to wait before checking again to see if a connection is free; default is 50 (wait 50 ms)")
		, Gfo_cmd_arg_itm_.opt_("http_server.special_pages_safelist").Example_("Random|XowaSearch|AllPages").Note_("specifies list of permitted special pages; special-page name is case-insensitive and must be separated by pipes (|); default is '' which permits all special pages")
		, Gfo_cmd_arg_itm_.sys_("show_license").Dflt_(true)
		, Gfo_cmd_arg_itm_.sys_("show_args").Dflt_(true)
		, Gfo_cmd_arg_itm_.new_(Gfo_cmd_arg_itm_.Tid_system, "help", Bool_.N, Gfo_cmd_arg_itm_.Val_tid_string)
		);
		return new Xoa_cmd_arg_mgr(arg_mgr);
	}
	private static final    String User_name_default = gplx.xowa.users.Xoue_user.Key_xowa_user;
	public static String Bin_dir_name() {
		String rv = "";
		Op_sys op_sys = Op_sys.Cur();
		switch (op_sys.Tid()) {
			case Op_sys.Tid_lnx: rv = "linux"; break;
			case Op_sys.Tid_wnt: rv = "windows"; break;
			case Op_sys.Tid_osx: rv = "macosx"; break;
			case Op_sys.Tid_arm: rv = "arm"; break;
			default: throw Err_.new_unhandled("unknown platform " + Op_sys.Cur());
		}
		if (op_sys.Bitness() == Op_sys.Bitness_64) rv += "_64";
		return rv;
	}
}
