SUMMARY = "Ngraph"

SRC_URI = "git://github.com/NervanaSystems/ngraph.git;protocol=https;branch=r0.25.1 \
           "

SRCREV = "005c11896de6d297a631025cb9ebfa17c76e55dd"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

inherit cmake

S = "${WORKDIR}/git"

