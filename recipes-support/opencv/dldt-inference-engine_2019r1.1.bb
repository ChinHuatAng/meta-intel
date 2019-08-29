SUMMARY = "OpenVINO Inference Engine"

SRC_URI = "git://github.com/opencv/dldt.git;protocol=git;branch=2019 \
           https://download.01.org/opencv/2019/openvinotoolkit/R1/inference_engine/firmware_ma2450_491.zip;name=ma2450 \
           https://download.01.org/opencv/2019/openvinotoolkit/R1/inference_engine/firmware_ma2480_mdk_R7_9.zip;name=ma2480 \
           file://0001-fix-openmp.patch;patchdir=../ \
           file://0001-supply-ade.patch;patchdir=../ \
           file://0001-use-system-omp.patch;patchdir=../ \
           file://0001-Supply-firmware-at-build-time.patch;patchdir=../ \
           file://0001-use-provided-paths.patch;patchdir=../ \
           file://0001-disable-tests.patch;patchdir=../ \
           file://0001-disable-werror.patch;patchdir=../ \
           file://0006-Install-sample-apps.patch;patchdir=../ \
           file://0017-use-system-pugixml.patch;patchdir=../ \
           file://0001-format_reader-install-library.patch;patchdir=../ \
           file://0001-Add-fopenmp-compile-option-when-OpenMP-is-used.patch;patchdir=../ \
           "
SRCREV = "0ef92871b6dd9a9ceed16d184c4595d2618d526f"

SRC_URI[ma2450.md5sum] = "2886778e21ff3713b3ac69e3f43a1da8"
SRC_URI[ma2450.sha256sum] = "070c57192fa1d4c17c5b2ebf98ebc35323c5617d1d6ccc454308b33a7476c45b"

SRC_URI[ma2480.md5sum] = "a65e0ceab3a33bfe6eff58f2291cecec"
SRC_URI[ma2480.sha256sum] = "a8231f4f68a1f8eab99d003a43756a23079699be379b64dacf66ad4912a607b3"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

inherit cmake

S = "${WORKDIR}/git/inference-engine"

EXTRA_OECMAKE = " \
                  -DVPU_FIRMWARE_MA2450_FILE=../mvnc/MvNCAPI-ma2450.mvcmd \
                  -DVPU_FIRMWARE_MA2480_FILE=../mvnc/MvNCAPI-ma2480.mvcmd \
                  -DENABLE_INTEL_OMP=0 \
                  -DENABLE_OPENCV=1 \
                  -DENABLE_SAMPLES_CORE=1 \
                  -DENABLE_PLUGIN_RPATH=0 \
                  -DENABLE_GNA=0 \
                  -DPYTHON_EXECUTABLE=${HOSTTOOLS_DIR}/python2 \
                  -DTHREADING=OMP \
                  -DCMAKE_INSTALL_LOCAL_ONLY=OFF \
                  "

DEPENDS += "libusb1 ade mkl-dnn opencv pugixml"

PACKAGECONFIG[intel-compute-runtime] = "-DENABLE_CLDNN=1,-DENABLE_CLDNN=0,,intel-compute-runtime"

PACKAGECONFIG ?= "intel-compute-runtime"

# Workaround issue where cmake build isn't configured to support SOVERSION
FILES_${PN}_append = " ${libdir}/lib*${SOLIBSDEV}"
FILES_${PN}-dev_remove = "${libdir}/lib*${SOLIBSDEV}"

do_install_append () {

    # Workaround cmake build issue where the libraries are not properly
    # installed, eventually these should be fixed in inference engine's
    # cmake build.

    install ${B}/src/extension/libcpu_extension.so ${D}${libdir}
    install ${B}/src/vpu/myriad_plugin/libmyriadPlugin.so ${D}${libdir}
    install ${B}/src/inference_engine/libinference_engine.so ${D}${libdir}
    install ${B}/src/hetero_plugin/libHeteroPlugin.so ${D}${libdir}
    install ${B}/src/mkldnn_plugin/libMKLDNNPlugin.so ${D}${libdir}
    install ${B}/src/cldnn_engine/libclDNNPlugin.so ${D}${libdir}

}
