SUMMARY = "Open source Python library for rapid development of applications \
    that make use of innovative user interfaces, such as multi-touch apps."
HOMEPAGE = "https://kivy.org/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6d3bc49400e35d5a2279d14c40dcfb09"

inherit setuptools3 pkgconfig features_check cython

# Kivy's setup files only look for GLES libraries for Android, iOS, RPi,
# and mali-based OS's. We need to patch the setup file to tell Kivy setup
# that our machine has GLES libaries installed as well
# Also, if using SDL2 as backend, SDL2 needs to be configured with gles
SRC_URI = "git://github.com/kivy/kivy.git;protocol=https;branch=stable \
           file://0001-add-support-for-glesv2.patch \
           file://0001-Remove-old-Python-2-long-from-Cython-files-fixes-bui.patch \
           "

SRCREV = "20d74dcd30f143abbd1aa94c76bafc5bd934d5bd"


PACKAGES += "${PN}-examples"
FILES:${PN}-examples = "/usr/share/kivy-examples"

USE_WAYLAND = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '1', '0', d)}"
export USE_WAYLAND

# if using Wayland, let's use pure Wayland (and not XWayland)
# so do not build using X11 flag when we detect Wayland
USE_X11 = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland',  '0', \
       bb.utils.contains('DISTRO_FEATURES', 'x11',      '1', \
                                                        '0', d), d)}"
export USE_X11

# Use OpenGL ES 2.0 library
KIVY_GRAPHICS = "gles"
export KIVY_GRAPHICS

KIVY_CROSS_SYSROOT = "${RECIPE_SYSROOT}"
export KIVY_CROSS_SYSROOT

REQUIRED_DISTRO_FEATURES += "opengl gobject-introspection-data"

ANY_OF_DISTRO_FEATURES = "x11 wayland"

DEPENDS += " \
    gstreamer1.0 \
    gstreamer1.0-python \
    virtual/libsdl2 \
    libsdl2-ttf \
    libsdl2-image \
    libsdl2-mixer \
    pango \
"

RDEPENDS:${PN} = " \
    gstreamer1.0 \
    gstreamer1.0-python \
    libsdl2 \
    libsdl2-ttf \
    libsdl2-image \
    libsdl2-mixer \
    pango \
    python3 \
    python3-docutils \
    python3-fcntl \
    python3-image \
    python3-pillow \
    python3-pygments \
"

# http://errors.yoctoproject.org/Errors/Details/766917/
# python3-kivy/2.3.0/git/kivy/graphics/cgl_backend/cgl_gl.c:4568:52: error: assignment to 'void (*)(GLuint,  GLsizei,  const GLchar **, const GLint *)' {aka 'void (*)(unsigned int,  int,  const char **, const int *)'} from incompatible pointer type 'void (*)(GLuint,  GLsizei,  const GLchar * const*, const GLint *)' {aka 'void (*)(unsigned int,  int,  const char * const*, const int *)'} [-Wincompatible-pointer-types]
# python3-kivy/2.3.0/git/kivy/core/window/_window_sdl2.c:8781:23: error: passing argument 1 of 'SDL_SetEventFilter' from incompatible pointer type [-Wincompatible-pointer-types]
CFLAGS += "-Wno-error=incompatible-pointer-types"
