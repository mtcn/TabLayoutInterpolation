package example.duyal.mete.tablayoutinterpolation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_empty.*

class EmptyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_empty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dummyTextView.text = arguments!!.getString("text")

    }

    companion object {
        const val TAG = "EmptyFragment"
        private var fragment: EmptyFragment? = null

        fun newInstance(text: String): EmptyFragment {
//            if (fragment == null)
                fragment = EmptyFragment()

            val bundle = Bundle()
            bundle.putString("text", text)
            fragment!!.arguments = bundle

            return fragment as EmptyFragment
        }
    }
}