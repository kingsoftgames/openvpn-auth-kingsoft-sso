# openvpn-auth-kingsoft-sso

Script plugin for [OpenVPN][openvpn], using [Kingsoft SSO][kingsoft-sso] as authentication backend.

Built and tested on Ubuntu Server 18.04 LTS.

[openvpn]: https://openvpn.net/
[kingsoft-sso]: https://sso.kingsoft.com/

## Build

1. Install [Apache Maven](https://maven.apache.org/)

2. Install [GraalVM and native-image](https://quarkus.io/guides/building-native-image-guide)

3. Run `build.sh`

    ```bash
    export GRAALVM_HOME=<GraalVM Install Dir>
    ./build.sh
    ```

## Deploy

After build step, you need to deploy files in the `build` directory to the server running OpenVPN:

1. Configure environment variables in `build/auth-kingsoft-sso.conf`

2. Copy all files to `/opt/openvpn/` with owner `root`, with permissions:

    ```
    $ ll -h /opt/openvpn/
    total 22M
    drwxr-xr-x 2 root root 4.0K Oct 29 19:30 ./
    drwxr-xr-x 4 root root 4.0K Oct 29 17:44 ../
    -rw-r--r-- 1 root root  153 Oct 29 18:22 auth-kingsoft-sso.conf
    -rwxr-xr-x 1 root root  386 Oct 29 19:30 auth-kingsoft-sso.sh*
    -rwxr-xr-x 1 root root 254K Oct 29 17:56 libsunec.so*
    -rwxr-xr-x 1 root root  22M Oct 29 17:56 openvpn-auth-kingsoft-sso*
    ```

3. Add the following to OpenVPN server-side configuration file:

    ```
    auth-user-pass-verify /opt/openvpn/auth-kingsoft-sso.sh via-file
    ```

4. Restart the OpenVPN service.
