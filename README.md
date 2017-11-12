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