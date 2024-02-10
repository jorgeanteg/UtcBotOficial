package com.example.utcbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "utcBot.db";
    private static final int DATABASE_VERSION = 2; // Incrementa la versión de la base de datos

    // Definir la estructura de la tabla para almacenar los datos del formulario
    static final String TABLE_NAME = "proyecto";
    static final String COLUMN_ID = "id";
    static final String COLUMN_NOMBRE = "nombre";
    static final String COLUMN_CONTENIDO = "contenido";

    // Nueva tabla para ejemplos
    static final String TABLE_EJEMPLO = "ejemplo";
    static final String COLUMN_EJEMPLO_ID = "id";
    static final String COLUMN_EJEMPLO_NOMBRE = "nombre";
    static final String COLUMN_EJEMPLO_CONTENIDO = "contenido";

    // Consulta SQL para crear las tablas
    private static final String CREATE_TABLE_PROYECTO =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOMBRE + " TEXT," +
                    COLUMN_CONTENIDO + " TEXT);";

    private static final String CREATE_TABLE_EJEMPLO =
            "CREATE TABLE " + TABLE_EJEMPLO + " (" +
                    COLUMN_EJEMPLO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_EJEMPLO_NOMBRE + " TEXT," +
                    COLUMN_EJEMPLO_CONTENIDO + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROYECTO);
        db.execSQL(CREATE_TABLE_EJEMPLO);
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
                db.execSQL(CREATE_TABLE_EJEMPLO); // Crear la nueva tabla de proyecto
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
        values.put(COLUMN_EJEMPLO_NOMBRE, "Bloques de Sonido");
        values.put(COLUMN_EJEMPLO_CONTENIDO, "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n" +
                "  <block type=\"play_n1\" id=\"@ejh3lhmE(Doj?aEQuJQ\" x=\"100\" y=\"50\">\n" +
                "    <statement name=\"cuerpo\">\n" +
                "      <block type=\"audio_n1\" id=\"eNMgovfD7YZ:a4/bZ*2O\">\n" +
                "        <field name=\"AUDIO\">C</field>\n" +
                "        <field name=\"ESTADO_AUDIO\">blanca</field>\n" +
                "        <next>\n" +
                "          <block type=\"audio_n1\" id=\"=iMg6AWW8}c%dGhmE5Ry\">\n" +
                "            <field name=\"AUDIO\">C</field>\n" +
                "            <field name=\"ESTADO_AUDIO\">blanca</field>\n" +
                "            <next>\n" +
                "              <block type=\"audio_n1\" id=\"w)$H++ei|h5y9w1l2|W7\">\n" +
                "                <field name=\"AUDIO\">G</field>\n" +
                "                <field name=\"ESTADO_AUDIO\">negra</field>\n" +
                "                <next>\n" +
                "                  <block type=\"audio_n1\" id=\"d9r?;V:B|]mC;-[LaUAt\">\n" +
                "                    <field name=\"AUDIO\">G</field>\n" +
                "                    <field name=\"ESTADO_AUDIO\">negra</field>\n" +
                "                    <next>\n" +
                "                      <block type=\"audio_n1\" id=\"N}B+(k^1cj,RuX7=#}dC\">\n" +
                "                        <field name=\"AUDIO\">A</field>\n" +
                "                        <field name=\"ESTADO_AUDIO\">negra</field>\n" +
                "                        <next>\n" +
                "                          <block type=\"audio_n1\" id=\"j-MKO)C!6bY.M[+^8!+%\">\n" +
                "                            <field name=\"AUDIO\">A</field>\n" +
                "                            <field name=\"ESTADO_AUDIO\">negra</field>\n" +
                "                            <next>\n" +
                "                              <block type=\"audio_n1\" id=\"!V#}$HM~P}!8##;GHqb1\">\n" +
                "                                <field name=\"AUDIO\">G</field>\n" +
                "                                <field name=\"ESTADO_AUDIO\">blanca</field>\n" +
                "                                <next>\n" +
                "                                  <block type=\"audio_n1\" id=\"b0`xxoh%0}FE39t%h7IH\">\n" +
                "                                    <field name=\"AUDIO\">G</field>\n" +
                "                                    <field name=\"ESTADO_AUDIO\">blanca</field>\n" +
                "                                    <next>\n" +
                "                                      <block type=\"audio_n1\" id=\"=GX~-Q8_lGKR)cxvQGtr\">\n" +
                "                                        <field name=\"AUDIO\">F</field>\n" +
                "                                        <field name=\"ESTADO_AUDIO\">negra</field>\n" +
                "                                        <next>\n" +
                "                                          <block type=\"audio_n1\" id=\"l3*R:+0J@z7~XV=dzQ+0\">\n" +
                "                                            <field name=\"AUDIO\">F</field>\n" +
                "                                            <field name=\"ESTADO_AUDIO\">negra</field>\n" +
                "                                            <next>\n" +
                "                                              <block type=\"audio_n1\" id=\"7VcH{4G=/W;OF6Q$29}e\">\n" +
                "                                                <field name=\"AUDIO\">E</field>\n" +
                "                                                <field name=\"ESTADO_AUDIO\">negra</field>\n" +
                "                                                <next>\n" +
                "                                                  <block type=\"audio_n1\" id=\"xjI}Mo1_hF{.t01k1-K%\">\n" +
                "                                                    <field name=\"AUDIO\">E</field>\n" +
                "                                                    <field name=\"ESTADO_AUDIO\">negra</field>\n" +
                "                                                    <next>\n" +
                "                                                      <block type=\"audio_n1\" id=\"lCsz*c79B{lH]j|-gd1M\">\n" +
                "                                                        <field name=\"AUDIO\">D</field>\n" +
                "                                                        <field name=\"ESTADO_AUDIO\">blanca</field>\n" +
                "                                                        <next>\n" +
                "                                                          <block type=\"audio_n1\" id=\"_czpafHh46|fD$JhO3K/\">\n" +
                "                                                            <field name=\"AUDIO\">D</field>\n" +
                "                                                            <field name=\"ESTADO_AUDIO\">blanca</field>\n" +
                "                                                            <next>\n" +
                "                                                              <block type=\"audio_n1\" id=\"icjrZcgFBW07JMb~4=s+\">\n" +
                "                                                                <field name=\"AUDIO\">C</field>\n" +
                "                                                                <field name=\"ESTADO_AUDIO\">negra</field>\n" +
                "                                                                <next>\n" +
                "                                                                  <block type=\"audio_n1\" id=\"b5Qed?g1+OU;c+P$lSF3\">\n" +
                "                                                                    <field name=\"AUDIO\">G</field>\n" +
                "                                                                    <field name=\"ESTADO_AUDIO\">negra</field>\n" +
                "                                                                    <next>\n" +
                "                                                                      <block type=\"audio_n1\" id=\"@j-ht=+E3mF#LS4Yz:9B\">\n" +
                "                                                                        <field name=\"AUDIO\">A</field>\n" +
                "                                                                        <field name=\"ESTADO_AUDIO\">negra</field>\n" +
                "                                                                        <next>\n" +
                "                                                                          <block type=\"audio_n1\" id=\"wedtqaHr}Lg:T-R^2bVm\">\n" +
                "                                                                            <field name=\"AUDIO\">G</field>\n" +
                "                                                                            <field name=\"ESTADO_AUDIO\">negra</field>\n" +
                "                                                                            <next>\n" +
                "                                                                              <block type=\"audio_n1\" id=\"6pmp},)+ouX@bq)~uA2M\">\n" +
                "                                                                                <field name=\"AUDIO\">F</field>\n" +
                "                                                                                <field name=\"ESTADO_AUDIO\">blanca</field>\n" +
                "                                                                                <next>\n" +
                "                                                                                  <block type=\"audio_n1\" id=\"KwvhE(HvbF[]W]zQ)7AO\">\n" +
                "                                                                                    <field name=\"AUDIO\">E</field>\n" +
                "                                                                                    <field name=\"ESTADO_AUDIO\">blanca</field>\n" +
                "                                                                                    <next>\n" +
                "                                                                                      <block type=\"audio_n1\" id=\"IJR*%i%iDp?eL*1s,L_5\">\n" +
                "                                                                                        <field name=\"AUDIO\">C</field>\n" +
                "                                                                                        <field name=\"ESTADO_AUDIO\">blanca</field>\n" +
                "                                                                                      </block>\n" +
                "                                                                                    </next>\n" +
                "                                                                                  </block>\n" +
                "                                                                                </next>\n" +
                "                                                                              </block>\n" +
                "                                                                            </next>\n" +
                "                                                                          </block>\n" +
                "                                                                        </next>\n" +
                "                                                                      </block>\n" +
                "                                                                    </next>\n" +
                "                                                                  </block>\n" +
                "                                                                </next>\n" +
                "                                                              </block>\n" +
                "                                                            </next>\n" +
                "                                                          </block>\n" +
                "                                                        </next>\n" +
                "                                                      </block>\n" +
                "                                                    </next>\n" +
                "                                                  </block>\n" +
                "                                                </next>\n" +
                "                                              </block>\n" +
                "                                            </next>\n" +
                "                                          </block>\n" +
                "                                        </next>\n" +
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
        db.insert(TABLE_EJEMPLO, null, values);



        values.clear();
        values.put(COLUMN_EJEMPLO_NOMBRE, "Bucle For Básico ");
        values.put(COLUMN_EJEMPLO_CONTENIDO, "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n" +
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
        db.insert(TABLE_EJEMPLO, null, values);



        values.clear();

        values.put(COLUMN_EJEMPLO_NOMBRE, "Hexágono-Bucle-For");
        values.put(COLUMN_EJEMPLO_CONTENIDO, "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n" +
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
        db.insert(TABLE_EJEMPLO, null, values);

        values.clear();

        values.put(COLUMN_EJEMPLO_NOMBRE, "Octágono-bucle-for");
        values.put(COLUMN_EJEMPLO_CONTENIDO, "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n" +
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
        db.insert(TABLE_EJEMPLO, null, values);


        values.clear();

        values.put(COLUMN_EJEMPLO_NOMBRE, "Triángulo equilátero ");
        values.put(COLUMN_EJEMPLO_CONTENIDO, "<xml xmlns=\"https://developers.google.com/blockly/xml\">\n" +
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
        db.insert(TABLE_EJEMPLO, null, values);
        // Agrega más inserciones según sea necesario
    }
}
