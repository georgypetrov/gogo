package rest.api.test.sanity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import rest.api.test.sanity.suites.TestRequests;


@RunWith(Suite.class)
@SuiteClasses({ 
	
	TestRequests.class
	
	})

public class RunTests {
	// do nothing
}
