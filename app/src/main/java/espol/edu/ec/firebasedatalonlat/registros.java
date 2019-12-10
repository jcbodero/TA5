    package espol.edu.ec.firebasedatalonlat;

    import androidx.appcompat.app.AppCompatActivity;

    import android.os.Bundle;
    import android.widget.LinearLayout;
    import android.widget.TextView;

    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.util.LinkedList;

    public class registros extends AppCompatActivity {
        DatabaseReference db_reference;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registros);
            db_reference = FirebaseDatabase.getInstance().getReference().child("GPS");
            leerRegistros();
        }

        public void leerRegistros(){  db_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinkedList<String[]> ultima = new LinkedList<String[]>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mostrarRegistrosPorPantalla(snapshot,ultima);
                }
                String ultimo[] = ultima.getLast();
                String anterior[] = ultima.get(ultima.size()-2);
                if(ultimo[0] != anterior[0] || ultimo[1] != anterior[1]){
                   System.out.println("DIFERENTE");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println(error.toException());
            }
        });
        }
        public void mostrarRegistrosPorPantalla(DataSnapshot snapshot, LinkedList<String[]> ultima){
            LinearLayout contTemp = (LinearLayout) findViewById(R.id.ContenedorTemp);
            LinearLayout contAxis = (LinearLayout) findViewById(R.id.ContenedorAxis);

            String tempVal = String.valueOf(snapshot.child("Latitud").getValue());
            String axisVal = String.valueOf(snapshot.child("Longitud").getValue());

            TextView temp = new TextView(getApplicationContext());
            temp.setText(tempVal+" ° ");
            contTemp.addView(temp);

            TextView axis = new TextView(getApplicationContext());
            axis.setText(axisVal);     contAxis.addView(axis);

            ultima.add(new String[]{tempVal,axisVal});
        }
    }
