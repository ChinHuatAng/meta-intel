SUMMARY = "Common clang is a thin wrapper library around clang"
DESCRIPTION = "Common clang has OpenCL-oriented API and is capable \
 to compile OpenCL C kernels to SPIR-V modules."

LICENSE = "NCSA"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e8a15bf1416762a09ece07e44c79118c"

SRC_URI = "git://github.com/intel/opencl-clang.git;branch=ocl-open-80;protocol=https \
           file://point-to-correct-llvm-tblgen.patch \
           "

SRCREV = "94af090661d7c953c516c97a25ed053c744a0737"

S = "${WORKDIR}/git"

inherit cmake
DEPENDS += "clang"

DEPENDS_append_class-target = " opencl-clang-native"
LDFLAGS_append_class-native = " -fuse-ld=lld"

COMPATIBLE_HOST = '(x86_64).*-linux'
COMPATIBLE_HOST_libc-musl = "null"

EXTRA_OECMAKE += "\
                  -DLLVM_TABLEGEN_EXE=${STAGING_BINDIR_NATIVE}/llvm-tblgen \
                  -DCMAKE_SKIP_RPATH=TRUE \
                  "

do_install_append_class-native() {
        install -d ${D}${bindir}
        install -m 0755 ${B}/linux_linker/linux_resource_linker ${D}${bindir}/
}

BBCLASSEXTEND = "native nativesdk"

TOOLCHAIN_class-native = "clang"
