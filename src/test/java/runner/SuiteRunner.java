package runner;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import barrigarest.BarrigaRestTests;

@Suite
@SelectClasses({
	BarrigaRestTests.class
})
public class SuiteRunner {
    
}
