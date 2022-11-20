#include <mutex>
class barrier
{
public:
	barrier(int count) : thread_count(count), counter(0), waiting(0)
	{}

	void wait() {
		// fence mechanism
		std::unique_lock<std::mutex> lk(m);
		++counter;
		++waiting;
		cv.wait(lk, [&]{return counter >= thread_count;});
		cv.notify_one();
		--waiting;
		if (waiting == 0)
			counter = 0; // reset barrier
		lk.unlock();
	}

private:
	std::mutex m;
	std::condition_variable cv;
	int counter;
	int waiting;
	int thread_count;
};
