package com.glowhaven.ppt;

import static spark.Spark.get;
import static spark.Spark.port;

public class GlowHavenPptApplication {

    public static void main(String[] args) {
        port(8080);
        get("/", (req, res) -> "Glow Haven PPT API");
    }
}
