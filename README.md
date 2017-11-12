ETCD Scheduler for DC/OS
------------------------

# Etcd scheduler for DC/OS

![Image of schduler](frameworks/etcd/docs/etcd-scheduler.png)

# How to use 
## Build
```bash
$ cd frameworks/etcd/
$ ./build.sh
```

## Use Universe

* Serve `frameworks/etcd/build/distributions/*.zip` files in httpd.
* Serve `sdk/bootstrap/bootstrap` file in httpd.
* Copy `frameworks/etcd/universe/*` to `universe/repo/B/beta-etcd/0/*`
* Replace file assets in `resource.json` with:
```json
    {
      "jre-tar-gz": "https://downloads.mesosphere.com/java/jre-8u131-linux-x64-jce-unlimited.tar.gz",
      "libmesos-bundle-tar-gz": "https://downloads.mesosphere.io/libmesos-bundle/libmesos-bundle-1.10-1.4-63e0814.tar.gz"
    }
``` 

* Replace `bootstrap` asset in `resource.json` with proper location.
* Replcae `scheduler-zip` asset in `resource.json` with proper location.
* Replcae `executor-zip` asset in `resource.json` with proper location.

* Build local universe.

## Use pre-built docker image

TBD

# Limitations
* ETCD Docker image is static: `quay.io/coreos/etcd:v3.2`
* ETCD node count is static: `3`
* Scheduler does not provide cli extension.
* Scheduler does not provide endpoint to etcd. You can find etcd at `etcd-0-node.<framework-name>.autoip.dcos.thisdcos.directory`.
