/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.TOSHIBA.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.TOSHIBA.example.com",
                ownerName = "backend.myapplication.TOSHIBA.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    @ApiMethod(name = "checkPassengers")
    public MyResult checkPassengers(@Named("sector") String sector, @Named("core") String core, @Named("minimum") Long minimum) {

        Requested requested = new Requested(this);
        requested.checkIfRequested(core, sector);
        final MyResult response = new MyResult();
        response.setData(core);
        return response;
    }

}



