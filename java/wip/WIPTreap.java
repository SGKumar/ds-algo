import java.util.Random;

public class Treap
{
	static int rnd = new Random();

	static class TreapNode
	{
		int value, prio, size;
		TreapNode left, right;
		
		TreapNode(int val)
		{
			value = val;
			prio = rnd.nextInt();
			size = 1;
		}
		
		public TreapNode left()	{ return left; }
		public TreapNode right(){ return right; }
		
		public int size()	{ return size; }
		public void updateSize()
		{
			size = 1 + t.left().size() + t.right().size();
		}
	}

	static class TreapPair
	{
		TreapNode left;
		TreapNode right;

		TreapPair(TreapNode left, TreapNode right) {
			this.left = left;
			this.right = right;
		}
	}

	//void split(Treap *t, Treap *&l, Treap *&r, int pos, int add = 0)
	// We're not just splitting into 3 sub-trees and returning here.
	static TreapPair split(TreapNode root, int inPos, int add = 0, boolean mergeRemains = true)
	{
		if(root == null)
			return new TreapPair(null, null);
		
		int curPos = add + root.size() + 1;
		TreapPair split;
		if(inPos > curPos)
		{
			//split(t->l, l ,t->l, pos, add), r = t;
			split = split(root.Right, inPos, curPos);
			if(merge)
			{
				root.right = rightSplit.left;
				rightSplit.left = root;
			}
		}
		else
		{			
			//split(t->r, t->r, r, pos, cur_pos), l = t;	
			split = split(root.Left, inPos, add);
			if(merge)
			{
				root.left = split.right;
				split.right = root;
			}
		}
		root.updateSize();
		return split;
	}

	static void merge(TreapNode left, Treap *l, Treap *r)
	{
		if(!l || !r) t = l? l:r;
		else if(l->prior > r->prior) {merge(l->r, l->r, r); t = l;}
		else {merge(r->l, l, r->l);t = r;}
		upd_sz(t);
	}

void insert(TreapNode node, int pos, int val)
{
    TreapNode l, r, cur = new Treap(val);
    split(node, pos-1);
    merge(t, l, cur);
    merge(t, t, r);
}

void erase(Treap *&t, int pos)
{
    Treap *l, *r, *g;
    split(t, l, r, pos-1);
    split(r, g, r, 1);
    merge(t, l, r);
}

int find_kth(Treap *t, int k, int add = 0)
{
    assert(t);
    int cur_pos = add + sz(t->l) + 1;
    if(cur_pos == k) return t-> val;
    if(cur_pos < k) return find_kth(t->r, k, cur_pos);
    return find_kth(t->l, k, add);
}


int main()
{
    //freopen("in.txt", "r", stdin);
    int i, j, k, n, m, v;
    int type, l, r;
    Treap *leftPart, *rightPart, *middlePart;

    scanf("%d %d", &n, &m);
    for(i = 1; i <= n; i++)
    {
        scanf("%d", &v);
        insert(root, i, v);
    }

    for(i = 1; i <= m; i++)
    {
        scanf("%d %d %d", &type, &l, &r);

        split(root, leftPart, rightPart, l-1);
        split(rightPart, middlePart, rightPart, r-l+1);

        if(type == 1)
        {
            merge(root, middlePart, leftPart);
            merge(root, root, rightPart);
        }
        else
        {
            merge(root, leftPart, rightPart);
            merge(root, root, middlePart);
        }
    }

    printf("%d\n", abs(find_kth(root, 1) - find_kth(root, n)));
    for(i = 1; i <= n; i++)
        printf("%d ", find_kth(root, i));

    return 0;
}