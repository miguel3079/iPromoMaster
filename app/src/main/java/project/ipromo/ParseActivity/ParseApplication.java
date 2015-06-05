package project.ipromo.ParseActivity;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
        Parse.initialize(this, "TL06Yd6EASdGw77EdMzyf2XG03f9vR0WdbMqC1vF", "oHlxDUfAhGWpaYDnKJU3aQefpQxWgJ7PQEececAS");
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this
		// line.
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);
	}

}
