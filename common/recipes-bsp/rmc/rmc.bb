SUMMARY = "RMC (Runtime Machine Configuration)"

DESCRIPTION = "RMC project provides a tool and libraries to identify types \
of hardware boards and access any file-based data specific to the board's \
type at runtime in a centralized way. Software (clients) can have a generic \
logic to query board-specific data from RMC without knowing the type of board. \
This make it possible to have a generic software work running on boards which \
require any quirks or customizations at a board or product level. \
"

LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://COPYING;md5=838c366f69b72c5df05c96dff79b35f2"

SRC_URI = "git://git.yoctoproject.org/rmc"

SRCREV = "d28eaec425f05511f0d9cd82b3623e95980ff2f6"

S = "${WORKDIR}/git"

COMPATIBLE_HOST = "(x86_64.*|i.86.*)-linux*"

TARGET_CFLAGS +="-Wl,--hash-style=both"

EXTRA_OEMAKE = "RMC_INSTALL_PREFIX=${D}/${prefix} \
                RMC_INSTALL_BIN_PATH=${D}${bindir} \
                RMC_INSTALL_LIB_PATH=${D}${libdir} \
                RMC_INSTALL_HEADER_PATH=${D}${includedir}/rmc"

SECURITY_CFLAGS_remove_class-target = "-fstack-protector-strong"
do_compile_class-target() {
	oe_runmake
	oe_runmake -f Makefile.efi
}

do_install() {
	oe_runmake install
	oe_runmake -f Makefile.efi install
}

do_install_class-native() {
	install -d ${D}${STAGING_BINDIR_NATIVE}
	install -m 0755 ${S}/src/rmc ${D}${STAGING_BINDIR_NATIVE}
}

BBCLASSEXTEND = "native"
