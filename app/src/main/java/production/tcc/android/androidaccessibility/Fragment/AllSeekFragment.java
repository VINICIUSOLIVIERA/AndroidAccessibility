package production.tcc.android.androidaccessibility.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import production.tcc.android.androidaccessibility.Activity.CreateSeekActivity;
import production.tcc.android.androidaccessibility.Config.RetrofitConfig;
import production.tcc.android.androidaccessibility.Config.Util;
import production.tcc.android.androidaccessibility.Models.Seek;
import production.tcc.android.androidaccessibility.R;
import production.tcc.android.androidaccessibility.Services.SeekService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AllSeekFragment extends Fragment {

    private View view;
    private FrameLayout frame;
    private ListView list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_seek, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        list = getActivity().findViewById(R.id.frame_all_seek_list);

        Retrofit retrofit = new RetrofitConfig().init();
        SeekService service = retrofit.create(SeekService.class);
        Call<List<Seek>> call = service.allByUser(1L);
        call.enqueue(new Callback<List<Seek>>() {
            @Override
            public void onResponse(Call<List<Seek>> call, Response<List<Seek>> response) {
                if(response != null) {
                    List<Seek> seeks = response.body();
                    ArrayAdapter<Seek> adapter = new ArrayAdapter<Seek>(getContext(), android.R.layout.simple_expandable_list_item_1, seeks);
                    list.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Seek>> call, Throwable t) {
                // Code....
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Seek seek = (Seek) list.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), CreateSeekActivity.class);
                intent.putExtra("id", seek.getId());
                intent.putExtra("topic", seek.getTopic());
                intent.putExtra("description", seek.getDescription());
                intent.putExtra("lat", seek.getLat());
                intent.putExtra("lng", seek.getLng());
                startActivity(intent);
                return false;
            }
        });


    }
}
