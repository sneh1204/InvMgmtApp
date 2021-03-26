package com.example.invmgmt;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.invmgmt.exception.BaseException;

public class CommonAsync {

    public interface CommonAsyncInterface {

        void onWork() throws BaseException;

        void afterWork();

    }

    public static class Task extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;
        ViewObject vo;
        String exc;

        public Task(ViewObject vo) {
            this.vo = vo;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(vo.view.getContext());
            progressDialog.setMessage(vo.getActivity().getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                vo.ci.onWork();
            } catch (BaseException rec) {
                exc = rec.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            if (exc == null) {
                vo.ci.afterWork();
            } else {
                Toast.makeText(vo.getActivity(), exc, Toast.LENGTH_LONG).show();
            }
        }
    }

    public static class ViewObject {

        View view;

        CommonAsyncInterface ci;

        public ViewObject(View view, CommonAsyncInterface ci) {
            this.view = view;
            this.ci = ci;
        }

        public MainActivity getActivity() {
            return (MainActivity) this.view.getContext();
        }

    }

}
