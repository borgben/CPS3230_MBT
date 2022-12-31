package main;
import nz.ac.waikato.modeljunit.FsmModel;
public class MarketUMModelTest implements FsmModel{
	UserEnum userState = UserEnum.LOGGED_OUT;
	int noAlerts =0;
	
	@Override
	public UserEnum getState() {
		// TODO Auto-generated method stub
		return userState;
	}

	@Override
	public void reset(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

}
