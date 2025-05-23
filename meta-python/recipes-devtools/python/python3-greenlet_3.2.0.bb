SUMMARY = "Python lightweight in-process concurrent programming"
HOMEPAGE = "https://greenlet.readthedocs.io/en/latest/"
LICENSE = "MIT & PSF-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e95668d68e4329085c7ab3535e6a7aee \
                    file://LICENSE.PSF;md5=c106931d9429eda0492617f037b8f69a"

SRC_URI[sha256sum] = "1d2d43bd711a43db8d9b9187500e6432ddb4fafe112d082ffabca8660a9e01a7"

inherit pypi python_setuptools_build_meta

BBCLASSEXTEND = "native nativesdk"
