package com.mesosphere.sdk.etcd.api;

import com.mesosphere.sdk.http.ResponseUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A read-only API for accessing etcd status.
 */
@Path("/v1/etcd")
public class ETCDStatusResource {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String ETCD_PREFIX = "etcd-";
    private static final String ETCD_SUFFIX = "-node";
    private String FRAMEWORK_HOST;

    private CloseableHttpClient httpclient = HttpClients.createDefault();

    public ETCDStatusResource(String frameworkName) {
        this.FRAMEWORK_HOST = frameworkName.replace("/", "") + ".autoip.dcos.thisdcos.directory";
    }

    @GET
    @Path("/{id}/stats/{api}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatsResult(@PathParam("id") int etcdId, @PathParam("api") String api) {
        HttpGet request = new HttpGet(makeRequestHost(etcdId) + "/v2/stats/" + api);
        try {
            CloseableHttpResponse response = httpclient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                return ResponseUtils.jsonOkResponse(new JSONObject(EntityUtils.toString(response.getEntity())));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return Response.serverError().build();
    }

    private String makeRequestHost(int etcdId) {
        return "http://" + ETCD_PREFIX + etcdId + ETCD_SUFFIX + "." + FRAMEWORK_HOST;
    }
}
