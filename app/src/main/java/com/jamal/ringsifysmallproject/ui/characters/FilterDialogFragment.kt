package com.jamal.ringsifysmallproject.ui.characters

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.jamal.ringsifysmallproject.R
import com.jamal.ringsifysmallproject.databinding.DialogFilterBinding

class FilterDialogFragment : DialogFragment() {

    private var _binding: DialogFilterBinding? = null
    private val binding get() = _binding!!
    private val TAG = "FilterDialogFragment"
    private val viewModel by viewModels<FilterDialogViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "OnCreateDialog called")
        _binding = DialogFilterBinding.inflate(LayoutInflater.from(requireContext()))


        val races = resources.getStringArray(R.array.races)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, races)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView.dropDownHeight = ViewGroup.LayoutParams.WRAP_CONTENT

        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.FilterDialogTheme)

            builder.setView(binding.root)
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, id ->

                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}