package kr.ac.univ.common.validation;

import java.util.HashSet;
import java.util.Set;

public class FileType {
    public static final String EXE = "application/x-msdownload";
    public static final String EXE_PE32 = "application/x-msdownload; format=pe32";

    public static Set<String> invalidMimeTypeSet = new HashSet<String>();
    public static Set<String> invalidExtensionSet = new HashSet<String>();

    public static Set<String> validImageTypeSet = new HashSet<String>();

    // Invalid mime type Set
    static {
        // Application
        invalidMimeTypeSet.add("application/x-msdownload"); // .exe
        invalidMimeTypeSet.add("application/x-msdownload; format=pe32"); // .exe
        invalidMimeTypeSet.add("application/java-archive"); // .jar
        invalidMimeTypeSet.add("application/javascript"); // .js
        invalidMimeTypeSet.add("application/x-shockwave-flash"); // .swf
        invalidMimeTypeSet.add("application/octet-stream"); // .bin 등의 실행 파일
        invalidMimeTypeSet.add("application/x-msmetafile"); // .wmf
        invalidMimeTypeSet.add("application/java-vm"); // .class
        invalidMimeTypeSet.add("application/vnd.ms-htmlhelp"); // .chm
        invalidMimeTypeSet.add("image/x-portable-graymap"); // .pgm
        invalidMimeTypeSet.add("image/x-pcx"); // .pcx
        invalidMimeTypeSet.add("application/winhlp"); // .hlp
        invalidMimeTypeSet.add("application/vnd.americandynamics.acc"); // .acc
        invalidMimeTypeSet.add("text/css"); // .css
        invalidMimeTypeSet.add("application/x-sh"); //.sh
    }

    // Invalid Extension Set
    static {
        // Programs
        invalidExtensionSet.add(".pif");
        invalidExtensionSet.add(".gadget");
        invalidExtensionSet.add(".msi");
        invalidExtensionSet.add(".msp");
        invalidExtensionSet.add(".com");
        invalidExtensionSet.add(".hta");
        invalidExtensionSet.add(".cpl");
        invalidExtensionSet.add(".msc");
        invalidExtensionSet.add(".exe");
        // Scripts
        invalidExtensionSet.add(".bat");
        invalidExtensionSet.add(".cmd");
        invalidExtensionSet.add(".vb");
        invalidExtensionSet.add(".vbs");
        invalidExtensionSet.add(".vbe");
        invalidExtensionSet.add(".jse");
        invalidExtensionSet.add(".ws");
        invalidExtensionSet.add(".wsf");
        invalidExtensionSet.add(".wsc");
        invalidExtensionSet.add(".wsh");
        invalidExtensionSet.add(".ps1");
        invalidExtensionSet.add(".ps2");
        invalidExtensionSet.add(".ps1xml");
        invalidExtensionSet.add(".ps2xml");
        invalidExtensionSet.add(".psc1");
        invalidExtensionSet.add(".psc2");
        invalidExtensionSet.add(".msh");
        invalidExtensionSet.add(".msh1");
        invalidExtensionSet.add(".msh2");
        invalidExtensionSet.add(".mshxml");
        invalidExtensionSet.add(".msh1xml");
        invalidExtensionSet.add(".msh2xml");
        // Shortcuts
        invalidExtensionSet.add(".scf");
        invalidExtensionSet.add(".lnk");
        invalidExtensionSet.add(".inf");
        invalidExtensionSet.add(".reg");
        // Others
        invalidExtensionSet.add(".dll");
        invalidExtensionSet.add(".sys");
        invalidExtensionSet.add(".gzquar");
        invalidExtensionSet.add(".zix");
        invalidExtensionSet.add(".aru");
        invalidExtensionSet.add(".ozd");
        invalidExtensionSet.add(".drv");
        invalidExtensionSet.add(".sjs");
        invalidExtensionSet.add(".dev");
        invalidExtensionSet.add(".xlm");
        invalidExtensionSet.add(".0_full_0_tgod_signed");
        invalidExtensionSet.add(".boo");
        invalidExtensionSet.add(".tps");
        invalidExtensionSet.add(".tsa");
        invalidExtensionSet.add(".sop");
        invalidExtensionSet.add(".bkd");
        invalidExtensionSet.add(".cih");
        invalidExtensionSet.add(".iik");
        invalidExtensionSet.add(".dyz");
        invalidExtensionSet.add(".dyv");
        invalidExtensionSet.add(".kcd");
        invalidExtensionSet.add(".s7p");
        invalidExtensionSet.add("dlb");
        invalidExtensionSet.add(".9");
        invalidExtensionSet.add(".dom");
        invalidExtensionSet.add(".php3");
        invalidExtensionSet.add(".dxz");
        invalidExtensionSet.add(".mjg");
        invalidExtensionSet.add(".mfu");
        invalidExtensionSet.add(".cla");
        invalidExtensionSet.add(".hlw");
        invalidExtensionSet.add(".rsc_tmp");
        invalidExtensionSet.add(".mjz");
        invalidExtensionSet.add(".bup");
        invalidExtensionSet.add(".upa");
        invalidExtensionSet.add(".bhx");
        invalidExtensionSet.add(".mcq");
        invalidExtensionSet.add(".dli");
        invalidExtensionSet.add(".txs");
        invalidExtensionSet.add(".fnr");
        invalidExtensionSet.add(".xir");
        invalidExtensionSet.add(".xlv");
        invalidExtensionSet.add(".bxz");
        invalidExtensionSet.add(".cxq");
        invalidExtensionSet.add(".xdu");
        invalidExtensionSet.add(".ska");
        invalidExtensionSet.add(".wlpginstall");
        invalidExtensionSet.add(".cfxxe");
        invalidExtensionSet.add(".tti");
        invalidExtensionSet.add(".vexe");
        invalidExtensionSet.add(".qrn");
        invalidExtensionSet.add(".dllx");
        invalidExtensionSet.add(".faq");
        invalidExtensionSet.add(".xtbl");
        invalidExtensionSet.add(".smtmp");
        invalidExtensionSet.add(".ceo");
        invalidExtensionSet.add(".tko");
        invalidExtensionSet.add(".uzy");
        invalidExtensionSet.add(".oar");
        invalidExtensionSet.add(".bll");
        invalidExtensionSet.add(".plc");
        invalidExtensionSet.add(".spam");
        invalidExtensionSet.add(".ssy");
        invalidExtensionSet.add(".dbd");
        invalidExtensionSet.add(".smm");
        invalidExtensionSet.add(".ce0");
        invalidExtensionSet.add(".zvz");
        invalidExtensionSet.add(".cc");
        invalidExtensionSet.add(".blf");
        invalidExtensionSet.add(".ctbl");
        invalidExtensionSet.add(".iws");
        invalidExtensionSet.add(".vzr");
        invalidExtensionSet.add(".nls");
        invalidExtensionSet.add(".hsq");
        invalidExtensionSet.add(".lkh");
        invalidExtensionSet.add(".aepl");
        invalidExtensionSet.add(".rna");
        invalidExtensionSet.add(".hts");
        invalidExtensionSet.add(".let");
        invalidExtensionSet.add(".aut");
        invalidExtensionSet.add(".delf");
        invalidExtensionSet.add(".buk");
        invalidExtensionSet.add(".fuj");
        invalidExtensionSet.add(".atm");
        invalidExtensionSet.add(".ezt");
        invalidExtensionSet.add(".fjl");
        invalidExtensionSet.add(".bmw");
        invalidExtensionSet.add(".dx");
        invalidExtensionSet.add(".cyw");
        invalidExtensionSet.add(".iva");
        invalidExtensionSet.add(".pid");
        invalidExtensionSet.add(".bps");
        invalidExtensionSet.add(".capxml");
        invalidExtensionSet.add(".bqf");
        invalidExtensionSet.add(".pr");
        invalidExtensionSet.add(".qit");
        invalidExtensionSet.add(".xnt");
        invalidExtensionSet.add(".lpaq5");
        invalidExtensionSet.add(".lok");
        invalidExtensionSet.add(".shs");
        invalidExtensionSet.add(".mcs");
        invalidExtensionSet.add(".dmg");
        invalidExtensionSet.add(".grp");
        invalidExtensionSet.add(".ocx");
        invalidExtensionSet.add(".ovl");
        invalidExtensionSet.add(".vdl");
        invalidExtensionSet.add(".vxd");
        invalidExtensionSet.add(".asp");
        invalidExtensionSet.add(".htx");
        invalidExtensionSet.add(".php");
        invalidExtensionSet.add(".crt");
        invalidExtensionSet.add(".ins");
        invalidExtensionSet.add(".isp");
        invalidExtensionSet.add(".sbs");
        invalidExtensionSet.add(".sct");
        invalidExtensionSet.add(".shb");
        invalidExtensionSet.add(".shd");
        invalidExtensionSet.add(".wst");
    }

    // Valid image mime type set
    static {
        validImageTypeSet.add("image/jpeg"); // .jpg, .jpeg
        validImageTypeSet.add("image/x-citrix-jpeg"); // .jpg, .jpeg
        validImageTypeSet.add("image/png"); //.png
        validImageTypeSet.add("image/x-citrix-png"); //.png
        validImageTypeSet.add("image/x-png"); //.png
    }

}