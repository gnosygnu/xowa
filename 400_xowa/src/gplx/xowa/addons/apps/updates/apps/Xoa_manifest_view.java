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
package gplx.xowa.addons.apps.updates.apps; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.updates.*;
import gplx.core.envs.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.*;
import javax.swing.*;
import javax.swing.border.Border;
public class Xoa_manifest_view extends JFrame implements Gfo_invk {
	private final    Xoa_manifest_wkr wkr;
	private String run_xowa_cmd;
	public Xoa_manifest_view(Io_url manifest_url) {
				super("XOWA Application Update");
		// init window
		this.setTitle("XOWA Application Update");
	    try {
	    	UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
	    }
	    catch (Exception e) {System.out.println(e.getMessage());}
	    this.setSize(700, 580);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setBackground(Color.WHITE);

		// init panel
		JPanel main_panel = new JPanel();
		main_panel.setSize(700, 580);
		this.setContentPane(main_panel);
		New_text_area(main_panel);
		run_xowa_lbl = New_link_lbl(this, main_panel, Invk__run_xowa, "<html><a href=\"\" style='color:#b9b9b9;'>Run XOWA</a></html>");
		this.setVisible(true);
				this.wkr = new Xoa_manifest_wkr(this);
		wkr.Init(manifest_url);
	}
		private JTextArea text_area;
	private JLabel run_xowa_lbl;
	private JScrollPane New_text_area(JPanel owner) {
		// init textarea
		text_area = new JTextArea();
		text_area.setForeground(Color.BLACK);
		text_area.setBackground(Color.WHITE);
		text_area.setMargin(new Insets(0, 0, 0,0));
		text_area.setLineWrap(true);
		text_area.setWrapStyleWord(true);	// else text will wrap in middle of words
		text_area.setCaretColor(Color.BLACK);
		text_area.getCaret().setBlinkRate(0);
		
		// init scrollpane
		JScrollPane text_scroll_pane = new JScrollPane(text_area);
		text_scroll_pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		owner.add(text_scroll_pane, BorderLayout.CENTER);
		text_scroll_pane.setPreferredSize(new Dimension(675, 500));
		return text_scroll_pane;
	}
	private static JLabel New_link_lbl(Gfo_invk invk, JPanel owner, String invk_cmd, String text) {
		JLabel rv = new JLabel(); 
		rv.setText(text);
		rv.setPreferredSize(new Dimension(80, 20));
		owner.add(rv, BorderLayout.PAGE_END);
		return rv;
	}
		public void Append(String s) {
				text_area.setText(text_area.getText() + "> " + s + "\n");
			}
	public void Mark_done(String s) {
		this.run_xowa_cmd = s;
				run_xowa_lbl.setText("<html><a href=\"\"'>Run XOWA</a></html>");
		run_xowa_lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
		run_xowa_lbl.addMouseListener(new Swing_mouse_adapter(Gfo_invk_cmd.New_by_key(this, Invk__run_xowa)));
			}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__run_xowa)) {
			Runtime_.Exec(run_xowa_cmd);
			System_.Exit();
		}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final    String Invk__run_xowa = "run_xowa";

	public static void Run() {
		Io_url manifest_url = Env_.AppUrl().GenNewExt(".txt");
		if (!Op_sys.Cur().Tid_is_osx())
			new Xoa_manifest_view(manifest_url);
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
