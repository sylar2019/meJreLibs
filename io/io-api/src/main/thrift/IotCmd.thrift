namespace java me.java.library.io.api

service CmdPostService {
    string postCmd(1: CmdDefinition cmdDefinition);
}


struct CmdDefinition{
    1: string cmdClass;
    2: string cmdJsonText;
}