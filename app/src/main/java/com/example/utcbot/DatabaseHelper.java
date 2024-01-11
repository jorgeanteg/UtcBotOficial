package com.example.utcbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "formulario.db";
    private static final int DATABASE_VERSION = 2; // Incrementa la versión de la base de datos

    // Definir la estructura de la tabla para almacenar los datos del formulario
    static final String TABLE_NAME = "formulario_table";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NOMBRE = "nombre";
    static final String COLUMN_CONTENIDO = "contenido";

    // Nueva tabla para proyectos
    static final String TABLE_PROYECTO = "proyecto_table";
    static final String COLUMN_PROYECTO_ID = "id";
    static final String COLUMN_PROYECTO_CUERPO = "cuerpo";
    static final String COLUMN_PROYECTO_DESCRIPCION = "descripcion";

    // Consulta SQL para crear las tablas
    private static final String CREATE_TABLE_FORMULARIO =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOMBRE + " TEXT," +
                    COLUMN_CONTENIDO + " TEXT);";

    private static final String CREATE_TABLE_PROYECTO =
            "CREATE TABLE " + TABLE_PROYECTO + " (" +
                    COLUMN_PROYECTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PROYECTO_CUERPO + " TEXT," +
                    COLUMN_PROYECTO_DESCRIPCION + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FORMULARIO);
        db.execSQL(CREATE_TABLE_PROYECTO);

        // Insertar datos iniciales en la tabla de proyectos al instalar la aplicación
        insertarDatosIniciales(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes agregar lógica para manejar actualizaciones de base de datos.
        // Si necesitas realizar cambios en la estructura de la base de datos, este es el lugar para hacerlo.

        // Puedes usar un switch para manejar diferentes versiones si tienes múltiples actualizaciones a lo largo del tiempo.
        switch (oldVersion) {
            case 1:
                // Código para actualización desde la versión 1 a la versión 2
                db.execSQL(CREATE_TABLE_PROYECTO); // Crear la nueva tabla de proyecto
                // Puedes agregar más código si es necesario para la actualización desde la versión 1
            case 2:
                // Insertar datos iniciales en la tabla de proyectos al actualizar la aplicación
                insertarDatosIniciales(db);
                // Puedes agregar código adicional para actualizaciones futuras
                break;
            default:
                throw new IllegalStateException("onUpgrade sin lógica para la versión: " + oldVersion);
        }
    }

    // Método para insertar datos iniciales en la tabla de proyectos
    private void insertarDatosIniciales(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROYECTO_CUERPO, "Movimientos Básicos");
        values.put(COLUMN_PROYECTO_DESCRIPCION, "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n" +
                "  <block type=\"play_n1\" id=\"D=LBGxxGEO{p$v=oki%}\" x=\"100\" y=\"50\">\n" +
                "    <statement name=\"cuerpo\">\n" +
                "      <block type=\"rotulador_n1\" id=\"Vsw4JH}UZdT4MVigaTLC\">\n" +
                "        <field name=\"ESTADO_ROTU\">True</field>\n" +
                "        <next>\n" +
                "          <block type=\"avanzar_n1\" id=\"h+flEnLwoNGv8P@%nDzW\">\n" +
                "            <field name=\"DISTANCIA\">20</field>\n" +
                "            <next>\n" +
                "              <block type=\"girar_derecha_n1\" id=\"i4.#$(0w}YMaG/6rI-|n\">\n" +
                "                <field name=\"ANGULO\">90</field>\n" +
                "                <next>\n" +
                "                  <block type=\"avanzar_n1\" id=\"z(pFhliW,=^`C!R8@#8o\">\n" +
                "                    <field name=\"DISTANCIA\">20</field>\n" +
                "                    <next>\n" +
                "                      <block type=\"girar_derecha_n1\" id=\"rKAj7{xojko1`3p1pHd7\">\n" +
                "                        <field name=\"ANGULO\">90</field>\n" +
                "                        <next>\n" +
                "                          <block type=\"avanzar_n1\" id=\"]RB2reEWp]tZ)Af:-o.G\">\n" +
                "                            <field name=\"DISTANCIA\">20</field>\n" +
                "                            <next>\n" +
                "                              <block type=\"girar_derecha_n1\" id=\"H_uqe5eU=:H9hCq{%hVp\">\n" +
                "                                <field name=\"ANGULO\">90</field>\n" +
                "                                <next>\n" +
                "                                  <block type=\"avanzar_n1\" id=\"yRI/=]TKp4s4=W`k/d43\">\n" +
                "                                    <field name=\"DISTANCIA\">20</field>\n" +
                "                                    <next>\n" +
                "                                      <block type=\"girar_derecha_n1\" id=\";1Jl#!=R;pXv)zazWLkt\">\n" +
                "                                        <field name=\"ANGULO\">90</field>\n" +
                "                                      </block>\n" +
                "                                    </next>\n" +
                "                                  </block>\n" +
                "                                </next>\n" +
                "                              </block>\n" +
                "                            </next>\n" +
                "                          </block>\n" +
                "                        </next>\n" +
                "                      </block>\n" +
                "                    </next>\n" +
                "                  </block>\n" +
                "                </next>\n" +
                "              </block>\n" +
                "            </next>\n" +
                "          </block>\n" +
                "        </next>\n" +
                "      </block>\n" +
                "    </statement>\n" +
                "  </block>\n" +
                "</xml>");
        db.insert(TABLE_PROYECTO, null, values);

        values.clear();

        values.put(COLUMN_PROYECTO_CUERPO, "Bucle For Básico ");
        values.put(COLUMN_PROYECTO_DESCRIPCION, "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n" +
                "  <block type=\"play_n1\" id=\"^4]UOYc!945baa)%K`Gf\" x=\"100\" y=\"50\">\n" +
                "    <statement name=\"cuerpo\">\n" +
                "      <block type=\"rotulador_n1\" id=\"9j}Tih=qHIz93oC5lh~W\">\n" +
                "        <field name=\"ESTADO_ROTU\">True</field>\n" +
                "        <next>\n" +
                "          <block type=\"foco_n1\" id=\"+XFGS-1n=7(~0,1t~MG@\">\n" +
                "            <field name=\"COLOR\">lightblue</field>\n" +
                "            <field name=\"ESTADO_FOCO\">parpadeando</field>\n" +
                "            <field name=\"ROJO\">173</field>\n" +
                "            <field name=\"VERDE\">216</field>\n" +
                "            <field name=\"AZUL\">230</field>\n" +
                "            <next>\n" +
                "              <block type=\"repetir_n1\" id=\"jx*Yd?UF5hiy5q+jhR?/\">\n" +
                "                <field name=\"VECES\">4</field>\n" +
                "                <statement name=\"repetir\">\n" +
                "                  <block type=\"avanzar_n1\" id=\"3cg+%c#=i9m^z6XnH$-h\">\n" +
                "                    <field name=\"DISTANCIA\">20</field>\n" +
                "                    <next>\n" +
                "                      <block type=\"girar_derecha_n1\" id=\"j-O_F9lUbp1#vpUQr7!t\">\n" +
                "                        <field name=\"ANGULO\">90</field>\n" +
                "                      </block>\n" +
                "                    </next>\n" +
                "                  </block>\n" +
                "                </statement>\n" +
                "                <next>\n" +
                "                  <block type=\"foco_n1\" id=\"[:A;]{Md`*pR*:^a8WT!\">\n" +
                "                    <field name=\"COLOR\">yellow</field>\n" +
                "                    <field name=\"ESTADO_FOCO\">apagadas</field>\n" +
                "                    <field name=\"ROJO\">255</field>\n" +
                "                    <field name=\"VERDE\">255</field>\n" +
                "                    <field name=\"AZUL\">0</field>\n" +
                "                  </block>\n" +
                "                </next>\n" +
                "              </block>\n" +
                "            </next>\n" +
                "          </block>\n" +
                "        </next>\n" +
                "      </block>\n" +
                "    </statement>\n" +
                "  </block>\n" +
                "</xml>");
        db.insert(TABLE_PROYECTO, null, values);



        values.clear();

        values.put(COLUMN_PROYECTO_CUERPO, "Hexagono-Bucle-For");
        values.put(COLUMN_PROYECTO_DESCRIPCION, "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n" +
                "  <block type=\"play_n2\" id=\"e1jOXdK2=`.Z*FJ|^Y%A\" x=\"100\" y=\"50\">\n" +
                "    <statement name=\"cuerpo\">\n" +
                "      <block type=\"rotulador_n2\" id=\"~#^R/lfz,1ZY+$?+B5+W\">\n" +
                "        <field name=\"ESTADO_ROTU\">True</field>\n" +
                "        <next>\n" +
                "          <block type=\"repetir_n2\" id=\"cFGl5$yVry1BkuKF51F0\">\n" +
                "            <field name=\"VECES\">6</field>\n" +
                "            <statement name=\"repetir\">\n" +
                "              <block type=\"avanzar_n2\" id=\"!_dWe(1!fqw7{(-3U:O2\">\n" +
                "                <field name=\"DISTANCIA\">50</field>\n" +
                "                <next>\n" +
                "                  <block type=\"girar_derecha_n2\" id=\"NGs1lvai]QOhb.-c!X#n\">\n" +
                "                    <field name=\"ANGULO\">60</field>\n" +
                "                  </block>\n" +
                "                </next>\n" +
                "              </block>\n" +
                "            </statement>\n" +
                "          </block>\n" +
                "        </next>\n" +
                "      </block>\n" +
                "    </statement>\n" +
                "  </block>\n" +
                "</xml>");
        db.insert(TABLE_PROYECTO, null, values);

        values.clear();

        values.put(COLUMN_PROYECTO_CUERPO, "Octagono-bucle-for");
        values.put(COLUMN_PROYECTO_DESCRIPCION, "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n" +
                "  <block type=\"play_n1\" id=\"D`wTdBb]_=zgVlp9W.=[\" x=\"100\" y=\"50\">\n" +
                "    <statement name=\"cuerpo\">\n" +
                "      <block type=\"audio_n1\" id=\"6yOlpb4)EH3}:3Rb~hRi\">\n" +
                "        <field name=\"AUDIO\">Fsos</field>\n" +
                "        <next>\n" +
                "          <block type=\"foco_n1\" id=\"#};l#-?@^b2,0yD`B@}3\">\n" +
                "            <field name=\"COLOR\">yellow</field>\n" +
                "            <field name=\"ESTADO_FOCO\">encendidas</field>\n" +
                "            <field name=\"ROJO\">255</field>\n" +
                "            <field name=\"VERDE\">255</field>\n" +
                "            <field name=\"AZUL\">0</field>\n" +
                "            <next>\n" +
                "              <block type=\"rotulador_n1\" id=\"QdvM(/:7U3Wtxa)!F[{U\">\n" +
                "                <field name=\"ESTADO_ROTU\">True</field>\n" +
                "                <next>\n" +
                "                  <block type=\"repetir_n1\" id=\"|nN$B._?1s{]aL.g+~*)\">\n" +
                "                    <field name=\"VECES\">8</field>\n" +
                "                    <statement name=\"repetir\">\n" +
                "                      <block type=\"avanzar_n1\" id=\"uXibT[LzXbPuA?g!l:#/\">\n" +
                "                        <field name=\"DISTANCIA\">50</field>\n" +
                "                        <next>\n" +
                "                          <block type=\"girar_derecha_n1\" id=\"93Dj7BbP{V=eZ,A{9IOl\">\n" +
                "                            <field name=\"ANGULO\">45</field>\n" +
                "                          </block>\n" +
                "                        </next>\n" +
                "                      </block>\n" +
                "                    </statement>\n" +
                "                    <next>\n" +
                "                      <block type=\"foco_n1\" id=\"VnlFI+L}PDo0_V(w4*cn\">\n" +
                "                        <field name=\"COLOR\">yellow</field>\n" +
                "                        <field name=\"ESTADO_FOCO\">apagadas</field>\n" +
                "                        <field name=\"ROJO\">255</field>\n" +
                "                        <field name=\"VERDE\">255</field>\n" +
                "                        <field name=\"AZUL\">0</field>\n" +
                "                      </block>\n" +
                "                    </next>\n" +
                "                  </block>\n" +
                "                </next>\n" +
                "              </block>\n" +
                "            </next>\n" +
                "          </block>\n" +
                "        </next>\n" +
                "      </block>\n" +
                "    </statement>\n" +
                "  </block>\n" +
                "</xml>");
        db.insert(TABLE_PROYECTO, null, values);


        values.clear();

        values.put(COLUMN_PROYECTO_CUERPO, "Triangulo equilátero ");
        values.put(COLUMN_PROYECTO_DESCRIPCION, "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n" +
                "  <block type=\"play_n2\" id=\"t+wXEOJH8*jl#jk#U2D@\" x=\"100\" y=\"50\">\n" +
                "    <statement name=\"cuerpo\">\n" +
                "      <block type=\"rotulador_n2\" id=\"oV!6pl)e9eFR;GwC[azo\">\n" +
                "        <field name=\"ESTADO_ROTU\">True</field>\n" +
                "        <next>\n" +
                "          <block type=\"girar_derecha_n2\" id=\"vtTG%bE@kjr{n@tQ)Pgq\">\n" +
                "            <field name=\"ANGULO\">60</field>\n" +
                "            <next>\n" +
                "              <block type=\"avanzar_n2\" id=\"vjrttSJt*sH0)rhb=vzI\">\n" +
                "                <field name=\"DISTANCIA\">100</field>\n" +
                "                <next>\n" +
                "                  <block type=\"girar_derecha_n2\" id=\"80)edmDO^#K{$nSn/aEv\">\n" +
                "                    <field name=\"ANGULO\">120</field>\n" +
                "                    <next>\n" +
                "                      <block type=\"avanzar_n2\" id=\";1aTCr5T%,SE]Fw^msB*\">\n" +
                "                        <field name=\"DISTANCIA\">100</field>\n" +
                "                        <next>\n" +
                "                          <block type=\"girar_derecha_n2\" id=\"U)@Zg=eK*BuY$$4[sgBU\">\n" +
                "                            <field name=\"ANGULO\">120</field>\n" +
                "                            <next>\n" +
                "                              <block type=\"avanzar_n2\" id=\"-OHW8j;4Ta8IYGyc_D/e\">\n" +
                "                                <field name=\"DISTANCIA\">100</field>\n" +
                "                              </block>\n" +
                "                            </next>\n" +
                "                          </block>\n" +
                "                        </next>\n" +
                "                      </block>\n" +
                "                    </next>\n" +
                "                  </block>\n" +
                "                </next>\n" +
                "              </block>\n" +
                "            </next>\n" +
                "          </block>\n" +
                "        </next>\n" +
                "      </block>\n" +
                "    </statement>\n" +
                "  </block>\n" +
                "</xml>");
        db.insert(TABLE_PROYECTO, null, values);
        // Agrega más inserciones según sea necesario
    }
}
