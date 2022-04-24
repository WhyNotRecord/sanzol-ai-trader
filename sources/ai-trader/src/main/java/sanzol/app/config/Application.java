package sanzol.app.config;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.IntelliJTheme;

import sanzol.app.forms.FrmMain;
import sanzol.app.task.BalanceService;
import sanzol.app.task.PositionService;
import sanzol.app.task.PriceService;
import sanzol.app.task.SignalService;

public final class Application
{
	private static String error = "";

	public static String getError()
	{
		return error;
	}

	public static void initialize()
	{
		try
		{
			PrivateConfig.loadKey();
			Config.load();
			PriceService.start();
			BalanceService.start();
			PositionService.start();
			SignalService.start();
		}
		catch (Exception e)
		{
			error = "Application.initialize: " + e.getMessage();
			System.err.println(e.getMessage());
		}
	}

	public static void initializeUI()
	{
		try
		{
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			UIManager.setLookAndFeel( new FlatIntelliJLaf() );
			//IntelliJTheme.setup( Application.class.getResourceAsStream("/resources/theme.json"));
		}
		catch (Exception e)
		{
			error = "Application.initializeUI: " + e.getMessage();
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args)
	{
		initialize();
		initializeUI();
		FrmMain.launch();
	}

}
