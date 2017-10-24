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
import gplx.langs.htmls.encoders.*; import gplx.core.envs.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.awt.*; import java.awt.event.*;
import javax.swing.*;
public class Xog_error_win extends JFrame implements Gfo_invk {
	private Xog_error_data error_data;
	public Xog_error_win(Xog_error_data error_data) {
				super("XOWA Error");
		this.setTitle("XOWA Error");
		this.error_data = error_data;
	    try {
	    	UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
	    }
	    catch (Exception e) {System.out.println(e.getMessage());}
	    this.setSize(700, 580);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setBackground(Color.WHITE);
		JPanel main_panel = new JPanel();
		main_panel.setSize(700, 580);
		this.setContentPane(main_panel);
		this.setLayout(null);
		new_text_area(main_panel, error_data);
		new_link_lbl(this, main_panel,  10, 520, Invk_open_site, "<html><a href=\"\">open issue</a></html>");
		new_link_lbl(this, main_panel, 605, 520, Invk_send_mail, "<html><a href=\"\">send email</a></html>");
		this.setVisible(true);
			}
		private static JScrollPane new_text_area(JPanel owner, Xog_error_data error_data) {
		JTextArea text_area = new JTextArea();
		text_area.setForeground(Color.BLACK);
		text_area.setBackground(Color.WHITE);
		text_area.setMargin(new Insets(0, 0, 0,0));
		text_area.setLineWrap(true);
		text_area.setWrapStyleWord(true);	// else text will wrap in middle of words
		text_area.setCaretColor(Color.BLACK);
		text_area.getCaret().setBlinkRate(0);
		text_area.setText(error_data.Full_msg());
		JScrollPane text_scroll_pane = new JScrollPane(text_area);
		text_scroll_pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		owner.add(text_scroll_pane);
		text_scroll_pane.setSize(675, 500);
		text_scroll_pane.setLocation(10, 10);
		return text_scroll_pane;
	}
	private static JLabel new_link_lbl(Gfo_invk invk, JPanel owner, int x, int y, String invk_cmd, String text) {
		JLabel rv = new JLabel(); 
		rv.setText(text);
		rv.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rv.addMouseListener(new Swing_mouse_adapter(Gfo_invk_cmd.New_by_key(invk, invk_cmd)));
		rv.setLocation(x, y);
		rv.setSize(80, 20);
		owner.add(rv);
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_send_mail)) {
			try {
				Gfo_url_encoder url_encoder = Gfo_url_encoder_.New__fsys_wnt().Make();
				String subject = url_encoder.Encode_str("XOWA boot error: " + error_data.Err_msg());
				String body = url_encoder.Encode_str(error_data.Err_details());
				Desktop.getDesktop().mail(new URI("mailto:gnosygnu+xowa_error_boot@gmail.com?subject=" + subject + "&body=" + body));
			} 
			catch (URISyntaxException ex) {}
			catch (IOException ex) {}			
		}
		else if	(ctx.Match(k, Invk_open_site)) {
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/gnosygnu/xowa/issues"));
			}
			catch (URISyntaxException ex) {}
			catch (IOException ex) {}			
		}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_send_mail = "send_mail", Invk_open_site = "open_site";
		public static void Run(String err_msg, String err_trace) {
		Xog_error_data error_data = Xog_error_data.new_(err_msg, err_trace);
		Gfo_usr_dlg_.Instance.Log_many("", "", error_data.Err_details());
		if (Op_sys.Cur().Tid_is_osx())
			gplx.core.consoles.Console_adp__sys.Instance.Write_str(error_data.Err_msg());
		else
			new Xog_error_win(error_data);
	}
}
class Swing_mouse_adapter extends MouseAdapter {
	private final Gfo_invk_cmd cmd;
	public Swing_mouse_adapter(Gfo_invk_cmd cmd) {this.cmd = cmd;}
	@Override public void mouseClicked(MouseEvent ev) {
		try {cmd.Exec();}
		catch (Exception e) {
			System.out.println(Err_.Message_gplx_full(e));
		}
	}
}
