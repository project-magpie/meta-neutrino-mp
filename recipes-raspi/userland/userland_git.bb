DESCRIPTION = "This repository contains the source code for the ARM side \
libraries used on Raspberry Pi. These typically are installed in /opt/vc/lib \
and includes source for the ARM side code to interface to: EGL, mmal, GLESv2,\
vcos, openmaxil, vchiq_arm, bcm_host, WFC, OpenVG."
LICENSE = "Broadcom"
LIC_FILES_CHKSUM = "file://LICENCE;md5=957f6640d5e2d2acfce73a36a56cb32f"

PR = "r3"

PROVIDES = "virtual/libgles2 \
            virtual/egl"
COMPATIBLE_MACHINE = "raspberrypi"

SRCREV = "628498eb6cf501554c98a5e6c7ea3855574fa43e"
SRC_URI = "git://github.com/raspberrypi/userland.git;protocol=git;branch=master \
          file://bcm_host.pc \
          file://egl.pc \
          file://openmaxil.pc \
          "
S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = "-DCMAKE_BUILD_TYPE=Release"

# The compiled binaries don't provide sonames.
SOLIBS = "${SOLIBSDEV}"

do_install_append() {
    mkdir -p ${D}/${prefix}
    mv ${D}/opt/vc/* ${D}/${prefix}
    rm -rf ${D}/opt

    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/bcm_host.pc  ${D}${libdir}/pkgconfig/
    install -m 0644 ${WORKDIR}/egl.pc       ${D}${libdir}/pkgconfig/
    install -m 0644 ${WORKDIR}/openmaxil.pc ${D}${libdir}/pkgconfig/
}

FILES_${PN} += "${libdir}/*${SOLIBS}"
FILES_${PN}-dev = "${includedir} \
                   ${libdir}/pkgconfig \
                   ${prefix}/src"
FILES_${PN}-doc += "${datadir}/install"

PACKAGE_ARCH = "${MACHINE_ARCH}"

