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
package gplx.xowa.apps.progs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.xowa.apps.fsys.*; import gplx.xowa.files.*; import gplx.core.envs.*;
public class Xoa_prog_mgr implements Gfo_invk {
	private Xoa_app app;
	private Gfo_usr_dlg usr_dlg;
	private Process_adp app_web;
	private Process_adp[] apps_by_ext = new Process_adp[Xof_ext_.Id__max];
	public void Init_by_app(Xoa_app app, Xoa_fsys_eval cmd_eval) {
		this.app = app;
		this.usr_dlg = Xoa_app_.Usr_dlg();
		Process_adp.ini_(this, usr_dlg, app_query_img_size			, cmd_eval, Process_adp.Run_mode_sync_timeout	, 10 * 60, "~{<>bin_plat_dir<>}imagemagick\\identify", "-ping -format \"<{%w,%h}>\" \"~{file}\"", "file");
		Process_adp.ini_(this, usr_dlg, app_resize_img				, cmd_eval, Process_adp.Run_mode_sync_timeout	, 10 * 60, "~{<>bin_plat_dir<>}imagemagick\\convert", "\"~{source}\" -coalesce -resize ~{width}x~{height} \"~{target}\"", "source", "target", "width", "height");
		Process_adp.ini_(this, usr_dlg, app_convert_svg_to_png		, cmd_eval, Process_adp.Run_mode_sync_timeout	, 10 * 60, "~{<>bin_plat_dir<>}inkscape\\inkscape", "-z -w ~{width} -f \"~{source}\" -e \"~{target}\"", "source", "target", "width").Thread_kill_name_("inkscape.exe");	// // -z=without-gui; -w=width; -f=file -e=export-png
		Process_adp.ini_(this, usr_dlg, app_convert_tex_to_dvi		, cmd_eval, Process_adp.Run_mode_sync_timeout	,  2 * 60, "~{<>bin_plat_dir<>}miktex\\miktex\\bin\\latex", "-quiet -output-directory=~{temp_dir} -job-name=xowa_temp ~{tex_file}", "tex_file", "temp_dir");
		Process_adp.ini_(this, usr_dlg, app_convert_dvi_to_png		, cmd_eval, Process_adp.Run_mode_sync_timeout	,  2 * 60, "~{<>bin_plat_dir<>}miktex\\miktex\\bin\\dvipng", "~{dvi_file} -o ~{png_file} -q* -T tight -bg Transparent", "dvi_file", "png_file", "temp_dir");
		Process_adp.ini_(this, usr_dlg, app_convert_djvu_to_tiff	, cmd_eval, Process_adp.Run_mode_sync_timeout	,  1 * 60, "~{<>bin_plat_dir<>}djvulibre\\ddjvu", "-format=tiff -page=1 \"~{source}\" \"~{target}\"", "source", "target");
		Process_adp.ini_(this, usr_dlg, app_decompress_bz2			, cmd_eval, Process_adp.Run_mode_sync_timeout	,  0	 , "~{<>bin_plat_dir<>}7-zip\\7za", "x -y \"~{src}\" -o\"~{trg_dir}\"", "src", "trg", "trg_dir");	// x=extract; -y=yes on all queries; -o=output_dir
		Process_adp.ini_(this, usr_dlg, app_decompress_bz2_by_stdout, cmd_eval, Process_adp.Run_mode_sync_timeout	,  0	 , "~{<>bin_plat_dir<>}7-zip\\7za", "x -so \"~{src}\"", "src");	// x=extract; -so=stdout
		Process_adp.ini_(this, usr_dlg, app_decompress_zip			, cmd_eval, Process_adp.Run_mode_sync_timeout	,  0	 , "~{<>bin_plat_dir<>}7-zip\\7za", "x -y \"~{src}\" -o\"~{trg_dir}\"", "src", "trg", "trg_dir");	// x=extract; -y=yes on all queries; -o=output_dir
		Process_adp.ini_(this, usr_dlg, app_decompress_gz			, cmd_eval, Process_adp.Run_mode_sync_timeout	,  0	 , "~{<>bin_plat_dir<>}7-zip\\7za", "x -y \"~{src}\" -o\"~{trg_dir}\"", "src", "trg", "trg_dir");	// x=extract; -y=yes on all queries; -o=output_dir
		Process_adp.ini_(this, usr_dlg, app_lua						, cmd_eval, Process_adp.Run_mode_async			,  0	 , "~{<>bin_plat_dir<>}lua\\lua", "", "");
		Process_adp.ini_(this, usr_dlg, app_lilypond				, cmd_eval, Process_adp.Run_mode_sync_timeout	,  1 * 60, "~{<>bin_plat_dir<>}lilypond\\usr\\bin\\lilypond.exe", "\"-dsafe=#t\" -dbackend=ps --png --header=texidoc -dmidi-extension=midi \"~{file}\"", "file");
		Process_adp.ini_(this, usr_dlg, app_abc2ly					, cmd_eval, Process_adp.Run_mode_sync_timeout	,  1 * 60, "~{<>bin_plat_dir<>}lilypond\\usr\\bin\\python.exe", "abc2ly.py -s \"--output=~{target}\" \"~{source}\"", "source", "target");
		Process_adp.ini_(this, usr_dlg, app_trim_img				, cmd_eval, Process_adp.Run_mode_sync_timeout	,  1 * 60, "~{<>bin_plat_dir<>}imagemagick\\convert", "-trim \"~{source}\"  \"~{target}\"", "source", "target");
		Process_adp.ini_(this, usr_dlg, app_midi_to_ogg				, cmd_eval, Process_adp.Run_mode_sync_timeout	,  1 * 60, "~{<>bin_plat_dir<>}timidity\\timidity", "-Ov \"--output-file=~{target}\" \"~{source}\"", "source", "target");
		Process_adp.ini_(this, usr_dlg, app_view_text				, cmd_eval, Process_adp.Run_mode_async			,  0	 , "cmd", "/c start \"~{url}\"", "url");

		for (int i = 0; i < apps_by_ext.length; i++) {
			apps_by_ext[i] = Process_adp.New(usr_dlg, cmd_eval, Process_adp.Run_mode_async, 0, "cmd", "/c start \"\" \"~{file}\"", "file");
		}
		app_web = Process_adp.New(usr_dlg, cmd_eval, Process_adp.Run_mode_async, 0, "cmd", "/c start \"\" \"~{url}\"", "url");
		app.Cfg().Bind_many_app(this
		, Cfg__web, Cfg__media, Cfg__image, Cfg__svg, Cfg__pdf, Cfg__djvu
		, Cfg__gz, Cfg__bz2, Cfg__bz2__stdout_cmd
		, Cfg__query_size, Cfg__resize_img, Cfg__convert_svg_to_png, Cfg__convert_djvu_to_tiff
		, Cfg__convert_tex_to_dvi, Cfg__convert_dvi_to_png
		, Cfg__lua
		, Cfg__lilypond, Cfg__abc2ly, Cfg__trim_img, Cfg__midi_to_ogg
		);
	}
	private Process_adp App_by_ext_key(String ext)		{return apps_by_ext[Xof_ext_.Get_id_by_ext_(Bry_.new_a7(ext))];}
	public Process_adp App_query_img_size()				{return app_query_img_size;}			private Process_adp app_query_img_size = new Process_adp();
	public Process_adp App_resize_img()					{return app_resize_img;}				private Process_adp app_resize_img = new Process_adp();
	public Process_adp App_convert_svg_to_png()			{return app_convert_svg_to_png;}		private Process_adp app_convert_svg_to_png = new Process_adp();
	public Process_adp App__tex_to_dvi()				{return app_convert_tex_to_dvi;}		private Process_adp app_convert_tex_to_dvi = new Process_adp();
	public Process_adp App__dvi_to_png()				{return app_convert_dvi_to_png;}		private Process_adp app_convert_dvi_to_png = new Process_adp();
	public Process_adp App_convert_djvu_to_tiff()		{return app_convert_djvu_to_tiff;}		private Process_adp app_convert_djvu_to_tiff = new Process_adp();
	public Process_adp App_view_text()					{return app_view_text;}					private Process_adp app_view_text = new Process_adp();
	public Process_adp App_decompress_bz2()				{return app_decompress_bz2;}			private Process_adp app_decompress_bz2 = new Process_adp();
	public Process_adp App_decompress_zip()				{return app_decompress_zip;}			private Process_adp app_decompress_zip = new Process_adp();
	public Process_adp App_decompress_gz()				{return app_decompress_gz;}				private Process_adp app_decompress_gz  = new Process_adp();
	public Process_adp App_decompress_bz2_by_stdout()	{return app_decompress_bz2_by_stdout;}	private Process_adp app_decompress_bz2_by_stdout = new Process_adp();
	public Process_adp App_lua()						{return app_lua;}						private Process_adp app_lua = new Process_adp();
	public Process_adp App_lilypond()					{return app_lilypond;}					private Process_adp app_lilypond = new Process_adp();
	public Process_adp App_abc2ly()						{return app_abc2ly;}					private Process_adp app_abc2ly = new Process_adp();
	public Process_adp App_trim_img()					{return app_trim_img;}					private Process_adp app_trim_img = new Process_adp();
	public Process_adp App_convert_midi_to_ogg()		{return app_midi_to_ogg;}		private Process_adp app_midi_to_ogg = new Process_adp();
	public Process_adp App_by_ext(String ext)			{return App_by_ext_key(String_.Mid(ext, 1));}	// ignore 1st . in ext; EX: ".png" -> "png"
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_convert_tex_to_dvi))			return app_convert_tex_to_dvi;
		else if	(ctx.Match(k, Invk_convert_dvi_to_png))			return app_convert_dvi_to_png;

		else if (String_.Eq(k, Cfg__web))						{Init_cmd(m.ReadStr("v"), app_web);}
		else if (String_.Eq(k, Cfg__media))						{Init_cmd(m.ReadStr("v"), Xof_ext_.Id_ogv, Xof_ext_.Id_webm, Xof_ext_.Id_flac, Xof_ext_.Id_ogg, Xof_ext_.Id_oga, Xof_ext_.Id_mid, Xof_ext_.Id_wav);}
		else if (String_.Eq(k, Cfg__image))						{Init_cmd(m.ReadStr("v"), Xof_ext_.Id_png, Xof_ext_.Id_jpg, Xof_ext_.Id_jpeg, Xof_ext_.Id_gif, Xof_ext_.Id_tif, Xof_ext_.Id_tiff, Xof_ext_.Id_bmp);}
		else if (String_.Eq(k, Cfg__svg))						{Init_cmd(m.ReadStr("v"), Xof_ext_.Id_svg);}
		else if (String_.Eq(k, Cfg__pdf))						{Init_cmd(m.ReadStr("v"), Xof_ext_.Id_pdf);}
		else if (String_.Eq(k, Cfg__djvu))						{Init_cmd(m.ReadStr("v"), Xof_ext_.Id_djvu);}
		else if (String_.Eq(k, Cfg__gz))						{Init_cmd(m.ReadStr("v"), app_decompress_gz);}
		else if (String_.Eq(k, Cfg__bz2))						{Init_cmd(m.ReadStr("v"), app_decompress_bz2);}
		else if (String_.Eq(k, Cfg__bz2__stdout_cmd))			{Init_cmd(m.ReadStr("v"), app_decompress_bz2_by_stdout);}
		else if	(String_.Eq(k, Cfg__query_size))				{Init_cmd(m.ReadStr("v"), app_query_img_size);}
		else if	(String_.Eq(k, Cfg__resize_img))				{Init_cmd(m.ReadStr("v"), app_resize_img);}
		else if	(String_.Eq(k, Cfg__convert_svg_to_png))		{Init_cmd(m.ReadStr("v"), app_convert_svg_to_png);}
		else if	(String_.Eq(k, Cfg__convert_djvu_to_tiff))		{Init_cmd(m.ReadStr("v"), app_convert_djvu_to_tiff);}
		else if	(String_.Eq(k, Cfg__convert_tex_to_dvi))		{Init_cmd(m.ReadStr("v"), app_convert_tex_to_dvi);}
		else if	(String_.Eq(k, Cfg__convert_dvi_to_png))		{Init_cmd(m.ReadStr("v"), app_convert_dvi_to_png);}
		else if (String_.Eq(k, Cfg__lua))						{Init_cmd(m.ReadStr("v"), app_lua); gplx.xowa.xtns.scribunto.Scrib_core_mgr.Term_all((Xoae_app)app);}
		else if (String_.Eq(k, Cfg__lilypond))					{Init_cmd(m.ReadStr("v"), app_lilypond);}
		else if (String_.Eq(k, Cfg__abc2ly))					{Init_cmd(m.ReadStr("v"), app_abc2ly);}
		else if (String_.Eq(k, Cfg__trim_img))					{Init_cmd(m.ReadStr("v"), app_trim_img);}
		else if (String_.Eq(k, Cfg__midi_to_ogg))				{Init_cmd(m.ReadStr("v"), app_midi_to_ogg);}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static void Init_cmd(String exe_and_args, Process_adp proc) {
		String[] lines = gplx.xowa.addons.apps.cfgs.Xocfg_mgr.Parse_io_cmd(exe_and_args);
		proc.Exe_and_args_(lines[0], lines[1]);
	}
	private void Init_cmd(String exe_and_args, int... exts) {
		String[] lines = gplx.xowa.addons.apps.cfgs.Xocfg_mgr.Parse_io_cmd(exe_and_args);
		for (int ext_id : exts) {
			apps_by_ext[ext_id].Exe_and_args_(lines[0], lines[1]);
		}
	}
	public void Exec_view_web(byte[] url) {
		String url_str = String_.new_u8(url);
		url_str = String_.Replace(url_str, "\"", "\"\""); // escape quotes; DATE:2013-03-31
		url_str = Process_adp.Escape_ampersands_if_process_is_cmd(Op_sys.Cur().Tid_is_wnt(), app_web.Exe_url().Raw(), url_str);	// escape ampersands; DATE:2014-05-20
		app_web.Run(url_str);
	}
	private static final String Invk_convert_tex_to_dvi = "convert_tex_to_dvi", Invk_convert_dvi_to_png = "convert_dvi_to_png";
	private static final String 
	  Cfg__web							= "xowa.files.apps.view.web"
	, Cfg__media						= "xowa.files.apps.view.media"
	, Cfg__image						= "xowa.files.apps.view.image"
	, Cfg__svg							= "xowa.files.apps.view.svg"
	, Cfg__pdf							= "xowa.files.apps.view.pdf"
	, Cfg__djvu							= "xowa.files.apps.view.djvu"

	, Cfg__query_size					= "xowa.files.apps.make.img_size_get"
	, Cfg__resize_img					= "xowa.files.apps.make.img_size_set"
	, Cfg__convert_svg_to_png			= "xowa.files.apps.make.svg_to_png"
	, Cfg__convert_djvu_to_tiff			= "xowa.files.apps.make.djvu_to_tiff"

	, Cfg__bz2__stdout_cmd				= "xowa.bldr.import.apps.bz2_stdout.cmd"
	, Cfg__bz2							= "xowa.bldr.import.apps.bz2"
	, Cfg__gz							= "xowa.bldr.import.apps.gz"

	, Cfg__lua							= "xowa.addon.scribunto.lua.cmd"
	, Cfg__convert_tex_to_dvi			= "xowa.addon.math.apps.tex_to_dvi"
	, Cfg__convert_dvi_to_png			= "xowa.addon.math.apps.dvi_to_png"
	, Cfg__lilypond						= "xowa.addon.score.apps.lilypond"
	, Cfg__abc2ly						= "xowa.addon.score.apps.abc2ly"
	, Cfg__trim_img						= "xowa.addon.score.apps.trim_img"
	, Cfg__midi_to_ogg					= "xowa.addon.score.apps.midi_to_ogg"
	;
}

