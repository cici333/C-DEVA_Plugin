package com.wuxuehong.plugin;

import java.util.HashMap;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.java.plugin.Plugin;

import com.wuxuehong.bean.Node;
import com.wuxuehong.interfaces.GraphInfo;
import com.wuxuehong.interfaces.NewAlgorithm;

public class TestAlgorithm extends Plugin implements NewAlgorithm {

	@Override
	public void drawCharts(String[] arg0, HashMap<String, Vector<Node>[]> arg1,
			Composite arg2, HashMap<String, RGB> arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAlgorithmName() {
		// TODO Auto-generated method stub
		return "Test Algorithm";
	}

	@Override
	public Vector<Node>[] getClusters(String[] arg0) {
		// TODO Auto-generated method stub
		Display.getDefault().asyncExec(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				MessageBox mb = new MessageBox(new Shell(),SWT.NONE);
				mb.setText("测试算法插件开发");
				mb.setMessage("当前蛋白质网络信息:节点数"+GraphInfo.nodelist.size()+"      边数:"+GraphInfo.edgelist.size());
				mb.open();
			}
		});
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "算法信息描述";
	}

	@Override
	public String getEvaluateNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParaValues() {
		// TODO Auto-generated method stub
		return new String[]{"0","1","2","3"};
	}

	@Override
	public String[] getParams() {
		// TODO Auto-generated method stub
		return new String[]{"param0","param1","param2","param3"};
	}

	@Override
	public int getStyle() {
		// TODO Auto-generated method stub
		return Algorithm;
	}

	@Override
	public void variableInit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doStart() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doStop() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
