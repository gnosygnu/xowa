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
package gplx.xowa.guis.views.boots; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.views.*;
import gplx.langs.htmls.encoders.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.awt.*; import java.awt.event.*;
import javax.swing.*;
public class Xog_error_win extends JFrame implements GfoInvkAble {
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
	private static JLabel new_link_lbl(GfoInvkAble invk, JPanel owner, int x, int y, String invk_cmd, String text) {
		JLabel rv = new JLabel(); 
		rv.setText(text);
		rv.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rv.addMouseListener(new Swing_mouse_adapter(GfoInvkAbleCmd.new_(invk, invk_cmd)));
		rv.setLocation(x, y);
		rv.setSize(80, 20);
		owner.add(rv);
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_send_mail)) {
			try {
				Url_encoder url_encoder = Xoa_app_.Utl__encoder_mgr().Fsys_safe();
				String subject = url_encoder.Encode_str("XOWA boot error: " + error_data.Err_msg());
				String body = url_encoder.Encode_str(error_data.Err_details());
				Desktop.getDesktop().mail(new URI("mailto:gnosygnu+xowa_error_boot@gmail.com?subject=" + subject + "&body=" + body));
			} catch (URISyntaxException | IOException ex) {
			}			
		}
		else if	(ctx.Match(k, Invk_open_site)) {
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/gnosygnu/xowa/issues"));
			} catch (URISyntaxException | IOException ex) {
			}			
		}
		else	return GfoInvkAble_.Rv_unhandled;
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
	private final GfoInvkAbleCmd cmd;
	public Swing_mouse_adapter(GfoInvkAbleCmd cmd) {this.cmd = cmd;}
	@Override public void mouseClicked(MouseEvent ev) {
		try {cmd.Invk();}
		catch (Exception e) {
			System.out.println(Err_.Message_gplx_full(e));
		}
	}
}
