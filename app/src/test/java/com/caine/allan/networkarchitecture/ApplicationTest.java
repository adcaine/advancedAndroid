package com.caine.allan.networkarchitecture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by allancaine on 2015-10-20.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class ApplicationTest {
    @Test
    public void testRobolectricWorks(){
        assertThat(1, equalTo(1));
    }
}
