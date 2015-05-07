/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xyw.dfood.listview;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

import java.util.ArrayList;

public class HeaderViewListAdapter implements WrapperListAdapter {

    static final ArrayList<ZrcListView.FixedViewInfo> EMPTY_INFO_LIST =
            new ArrayList<ZrcListView.FixedViewInfo>();
    private final ListAdapter mAdapter;
    ArrayList<ZrcListView.FixedViewInfo> mHeaderViewInfos;
    ArrayList<ZrcListView.FixedViewInfo> mFooterViewInfos;
    boolean mAreAllFixedViewsSelectable;

    public HeaderViewListAdapter(ArrayList<ZrcListView.FixedViewInfo> headerViewInfos,
            ArrayList<ZrcListView.FixedViewInfo> footerViewInfos, ListAdapter adapter) {
        mAdapter = adapter;

        if (headerViewInfos == null) {
            mHeaderViewInfos = EMPTY_INFO_LIST;
        } else {
            mHeaderViewInfos = headerViewInfos;
        }

        if (footerViewInfos == null) {
            mFooterViewInfos = EMPTY_INFO_LIST;
        } else {
            mFooterViewInfos = footerViewInfos;
        }

        mAreAllFixedViewsSelectable = areAllListInfosSelectable(mHeaderViewInfos)
                && areAllListInfosSelectable(mFooterViewInfos);
    }

    public int getHeadersCount() {
        return mHeaderViewInfos.size();
    }

    public int getFootersCount() {
        return mFooterViewInfos.size();
    }

    @Override
    public boolean isEmpty() {
        return mAdapter == null || mAdapter.isEmpty();
    }

    private boolean areAllListInfosSelectable(ArrayList<ZrcListView.FixedViewInfo> infos) {
        if (infos != null) {
            for (ZrcListView.FixedViewInfo info : infos) {
                if (!info.isSelectable) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean removeHeader(View v) {
        for (int i = 0; i < mHeaderViewInfos.size(); i++) {
            ZrcListView.FixedViewInfo info = mHeaderViewInfos.get(i);
            if (info.view == v) {
                mHeaderViewInfos.remove(i);

                mAreAllFixedViewsSelectable = areAllListInfosSelectable(mHeaderViewInfos)
                        && areAllListInfosSelectable(mFooterViewInfos);

                return true;
            }
        }

        return false;
    }

    public boolean removeFooter(View v) {
        for (int i = 0; i < mFooterViewInfos.size(); i++) {
            ZrcListView.FixedViewInfo info = mFooterViewInfos.get(i);
            if (info.view == v) {
                mFooterViewInfos.remove(i);

                mAreAllFixedViewsSelectable = areAllListInfosSelectable(mHeaderViewInfos)
                        && areAllListInfosSelectable(mFooterViewInfos);

                return true;
            }
        }

        return false;
    }

    @Override
    public int getCount() {
        if (mAdapter != null) {
            return getFootersCount() + getHeadersCount() + mAdapter.getCount();
        } else {
            return getFootersCount() + getHeadersCount();
        }
    }

    @Override
    public boolean areAllItemsEnabled() {
        if (mAdapter != null) {
            return mAreAllFixedViewsSelectable && mAdapter.areAllItemsEnabled();
        } else {
            return true;
        }
    }

    @Override
    public boolean isEnabled(int position) {
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return mHeaderViewInfos.get(position).isSelectable;
        }

        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getCount();
            if (adjPosition < adapterCount) {
                return mAdapter.isEnabled(adjPosition);
            }
        }

        return mFooterViewInfos.get(adjPosition - adapterCount).isSelectable;
    }

    @Override
    public Object getItem(int position) {
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return mHeaderViewInfos.get(position).data;
        }

        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItem(adjPosition);
            }
        }

        return mFooterViewInfos.get(adjPosition - adapterCount).data;
    }

    @Override
    public long getItemId(int position) {
        int numHeaders = getHeadersCount();
        if (mAdapter != null && position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = mAdapter.getCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemId(adjPosition);
            }
        }
        return -1;
    }

    @Override
    public boolean hasStableIds() {
        if (mAdapter != null) {
            return mAdapter.hasStableIds();
        }
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return mHeaderViewInfos.get(position).view;
        }

        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getView(adjPosition, convertView, parent);
            }
        }

        return mFooterViewInfos.get(adjPosition - adapterCount).view;
    }

    @Override
    public int getItemViewType(int position) {
        int numHeaders = getHeadersCount();
        if (mAdapter != null && position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = mAdapter.getCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        return ZrcAdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER;
    }

    @Override
    public int getViewTypeCount() {
        if (mAdapter != null) {
            return mAdapter.getViewTypeCount();
        }
        return 1;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(observer);
        }
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(observer);
        }
    }

    @Override
    public ListAdapter getWrappedAdapter() {
        return mAdapter;
    }
}
